#!/usr/bin/env bash
#
# Sample usage:
#   ./test_all.bash start stop
#   start and stop are optional
#
#   HOST=localhost PORT=7000 ./test-em-all.bash
#
# When not in Docker
#: ${HOST=localhost}
#: ${PORT=7000}

# When in Docker
: ${HOST=localhost}
: ${PORT=8080}

#array to hold all our test data ids
allTestMemberIds=()
allTestCatalogIds=()
allTestFineIds=()
allTestLoanId=()

function assertCurl() {

  local expectedHttpCode=$1
  local curlCmd="$2 -w \"%{http_code}\""
  local result=$(eval $curlCmd)
  local httpCode="${result:(-3)}"
  RESPONSE='' && (( ${#result} > 3 )) && RESPONSE="${result%???}"

  if [ "$httpCode" = "$expectedHttpCode" ]
  then
    if [ "$httpCode" = "200" ]
    then
      echo "Test OK (HTTP Code: $httpCode)"
    else
      echo "Test OK (HTTP Code: $httpCode, $RESPONSE)"
    fi
  else
      echo  "Test FAILED, EXPECTED HTTP Code: $expectedHttpCode, GOT: $httpCode, WILL ABORT!"
      echo  "- Failing command: $curlCmd"
      echo  "- Response Body: $RESPONSE"
      exit 1
  fi
}

function assertEqual() {

  local expected=$1
  local actual=$2

  if [ "$actual" = "$expected" ]
  then
    echo "Test OK (actual value: $actual)"
  else
    echo "Test FAILED, EXPECTED VALUE: $expected, ACTUAL VALUE: $actual, WILL ABORT"
    exit 1
  fi
}

#have all the microservices come up yet?
function testUrl() {
    url=$@
    if curl $url -ks -f -o /dev/null
    then
          echo "Ok"
          return 0
    else
          echo -n "not yet"
          return 1
    fi;
}

#prepare the test data that will be passed in the curl commands for posts and puts
function setupTestdata() {

##CREATE SOME Member TEST DATA - THIS WILL BE USED FOR THE POST REQUEST
#
body=\
'{
     "userId": "c195fd3a-3580-4b71-80f1-090000000000",
     "start_date": "2022-02-02",
     "end_date": "2030-02-02",
     "type": "FAMILY"
 }
'
    recreateMemberAggregate 1 "$body"


##CREATE SOME BOOKS TEST DATA- THIS WILL BE USED FOR THE POST REQUEST
body=\
'{
         "title": "1984",
         "type": "HARDCOVER",
         "quantities": 20,
         "isbn": "978-0-452-28423-4",
         "language": "FRENCH",
         "status": "AVAILABLE",
         "description": "A dystopian novel about totalitarianism and surveillance.",
         "authorId": "d80cfcf9-df70-4c8d-953d-233616ed8d7a"
     }
'
    recreateCatalogAggregate 1 "$body"



##CREATE SOME fine TEST DATA- THIS WILL BE USED FOR THE POST REQUEST
body=\
'{
         "issueDate": "2025-03-15",
         "status": "UNPAID",
         "finePayment": [
             {
                 "amount": 150.00,
                 "currency": "CAD",
                 "paymentDate": "2025-04-15",
                 "status": "FAILED",
                 "paymentMethod": "CREDIT"
             }
         ]
     }
'
    recreateFinesAggregate 1 "$body"


#
#Create some loan test data

body=\
  '{
       "loanDateStart": "2025-03-16",
       "loanDateEnd": "2025-04-16",
       "loanStatus": "ACTIVES",
       "catalogId": "3fe5c169-c1ef-42ea-9e5e-870f30ba9dd0",
       "bookId": "550e8400-e29b-41d4-a716-446655440003",
       "fineId": "cf7f8e5e-6f9b-4a5c-8d5e-3a4f001b8e4c"
   }'
    recreateLoanAggregate 1 "$body" "43b06e4a-c1c5-41d4-a716-446655440010"

} #end of setupTestdata


#USING USER TEST DATA - EXECUTE POST REQUEST
function recreateMemberAggregate() {
    local testId=$1
    local aggregate=$2

    #create the member and record the generated memberId
    memberId=$(curl -X POST http://$HOST:$PORT/api/v1/memberRecords -H "Content-Type:
    application/json" --data "$aggregate" | jq '.memberId')
    allTestMemberIds[$testId]=$memberId
    echo "Added Member with memberId: ${allTestMemberIds[$testId]}"
}



#USING CATALOG TEST DATA - EXECUTE POST REQUEST
function recreateCatalogAggregate() {
    local testId=$1
    local aggregate=$2
    local catalogId="3fe5c169-c1ef-42ea-9e5e-870f30ba9dd0"

    #create the book and record the generated bookId
    bookId=$(curl -X POST http://$HOST:$PORT/api/v1/catalogs/$catalogId/books -H "Content-Type:
    application/json" --data "$aggregate" | jq '.memberId')
    allTestCatalogIds[$testId]=bookId
    echo "Added Member with bookId: ${allTestCatalogIds[$testId]}"
}



#USING FINES TEST DATA - EXECUTE POST REQUEST
function recreateFinesAggregate() {
    local testId=$1
    local aggregate=$2

    #create the fine and record the generated fineId
    fineId=$(curl -X POST http://$HOST:$PORT/api/v1/fines -H "Content-Type:
    application/json" --data "$aggregate" | jq '.fineId')
    allTestFineIds[$testId]=fineId
    echo "Added Member with fineId: ${allTestFineIds[$testId]}"
}

#USING Loan TEST DATA - EXECUTE POST REQUEST
function recreateLoanAggregate() {
    local testId=$1
    local aggregate=$2
    local memberId=$3

    #create the loan and record the generated loanId
    loanId=$(curl -X POST http://$HOST:$PORT/api/v1/memberRecords/$memberId/loans -H "Content-Type:
    application/json" --data "$aggregate" | jq '.loanId')
    allTestLoanId[$testId]=$loanId
    echo "Added Loan with loanId: ${allTestLoanId[$testId]}"
}

#don't start testing until all the microservices are up and running
function waitForService() {
    url=$@
    echo -n "Wait for: $url... "
    n=0
    until testUrl $url
    do
        n=$((n + 1))
        if [[ $n == 100 ]]
        then
            echo " Give up"
            exit 1
        else
            sleep 6
            echo -n ", retry #$n "
        fi
    done
}

#start of test script
set -e

echo "HOST=${HOST}"
echo "PORT=${PORT}"

if [[ $@ == *"start"* ]]
then
    echo "Restarting the test environment..."
    echo "$ docker-compose down"
    docker-compose down
    echo "$ docker-compose up -d"
    docker-compose up -d
fi

#try to delete an entity/aggregate that you've set up but that you don't need. This will confirm that things are working
waitForService curl -X DELETE http://$HOST:$PORT/api/v1/memberRecords/43b06e4a-c1c5-41d4-a716-446655440010

setupTestdata

#EXECUTE EXPLICIT TESTS AND VALIDATE RESPONSE
#
##verify that a get all members works
echo -e "\nTest 1: Verify that a get all members works"
assertCurl 200 "curl http://$HOST:$PORT/api/v1/memberRecords -s"
assertEqual 10 $(echo $RESPONSE | jq ". | length")
#
#
### Verify that a normal get by id of earlier posted member works
#echo -e "\nTest 2: Verify that a normal get by id of earlier posted member works"
#assertCurl 200 "curl http://$HOST:$PORT/api/v1/members/${allTestMemberIds[1]} '${body}' -s"
#assertEqual ${allTestMemberIds[1]} $(echo $RESPONSE | jq .memberId)
#assertEqual "\"Adem\"" $(echo $RESPONSE | jq ".firstName")
##
##
## Verify that an update of an earlier posted member works - put at api-gateway has no response body
echo -e "\nTest 3: Verify that an update of an earlier posted member works"
body=\
'{
 	"bookId": "b1",
     "returned": "false"
 }'
assertCurl 200 "curl -X PUT http://$HOST:$PORT/api/v1/memberRecords/${allTestMemberIds[1]} -H \"Content-Type: application/json\" -d '${body}' -s"
#
#
## Verify that a delete of an earlier posted member works
echo -e "\nTest 4: Verify that a delete of an earlier posted member works"
assertCurl 204 "curl -X DELETE http://$HOST:$PORT/api/v1/memberRecords/${allTestMemberIds[1]} -s"
#
#
## Verify that a 404 (Not Found) status is returned for a non existing memberId (c3540a89-cb47-4c96-888e-ff96708db4d7)
echo -e "\nTest 5: Verify that a 404 (Not Found) error is returned for a get member request with a non existing memberId"
assertCurl 404 "curl http://$HOST:$PORT/api/v1/memberRecords/c3540a89-cb47-4c96-888e-ff96708db4d7 -s"
#
#
## Verify that a 422 (Unprocessable Entity) status is returned for an invalid memberId (c3540a89-cb47-4c96-888e-ff96708db4d)
echo -e "\nTest 6: Verify that a 422 (Unprocessable Entity) status is returned for a get member request with an invalid memberId"
assertCurl 422 "curl http://$HOST:$PORT/api/v1/memberRecords/c3540a89-cb47-4c96-888e-ff96708db4d -s"

#verify that a get all loans works
echo -e "\nTest 7: Verify that a get all loans works"
assertCurl 200 "curl http://$HOST:$PORT/api/v1/memberRecords/${allTestMemberIds[1]}/loans"
assertEqual 2 $(echo $RESPONSE | jq ". | length")

#verify that a get loan by id loanid and memberid works
echo -e "\nTest 8: Verify that a get loan by id loanid and memberid works"
assertCurl 200 "curl http://$HOST:$PORT/api/v1/memberRecords/c3540a89-cb47-4c96-888e-ff96708db4d/loans/${allTestLoanId[1]} -s"
assertEqual ${allTestLoanId[1]} $(echo $RESPONSE | jq .loanId)

#
##
##TODO: Add verification of vehicle status change to sold
#
## Verify that a delete of an earlier posted loan works
#echo -e "\nTest 10: Verify that a delete of an earlier posted loan works"
#assertCurl 204 "curl -X DELETE http://$HOST:$PORT/api/v1/members/m1/loans/${allTestLoanId[1]} -s"
#
## Verify that a 404 (Not Found) status is returned for a non existing loanId (c3540a89-cb47-4c96-888e-ff96708db4d7)
#echo -e "\nTest 11: Verify that a 404 (Not Found) error is returned for a get loan request with a non existing loanId"
#assertCurl 404 "curl http://$HOST:$PORT/api/v1/members/m1/loans/c3540a89-cb47-4c96-888e-ff96708db4d7 -s"
#
## Verify that a 422 (Unprocessable Entity) status is returned for an invalid loanId (c3540a89-cb47-4c96-888e-ff96708db4d)
#echo -e "\nTest 12: Verify that a 422 (Unprocessable Entity) status is returned for a get loan request with an invalid loanId"
#assertCurl 422 "curl http://$HOST:$PORT/api/v1/members/m1/loans/c3540a89-cb47-4c96-888e-ff96708db4d -s"
#
## Verify that a 404 (Not Found) status is returned for a non existing memberId (c3540a89-cb47-4c96-888e-ff96708db4d7)
#echo -e "\nTest 13: Verify that a 404 (Not Found) error is returned for a get loan request with a non existing memberId"
#assertCurl 404 "curl http://$HOST:$PORT/api/v1/members/c3540a89-cb47-4c96-888e-ff96708db4d7/loans -s"


if [[ $@ == *"stop"* ]]
then
    echo "We are done, stopping the test environment..."
    echo "$ docker-compose down"
    docker-compose down
fi
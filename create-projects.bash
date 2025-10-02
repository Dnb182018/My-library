#!/usr/bin/env bash

spring init \
--boot-version=3.4.4 \
--build=gradle \
--type=gradle-project \
--java-version=17 \
--packaging=jar \
--name=catalogs-service \
--package-name=com.library.catalogs \
--groupId=com.library.catalogs \
--dependencies=web,webflux,validation \
--version=1.0.0-SNAPSHOT \
catalogs-service

spring init \
--boot-version=3.4.4 \
--build=gradle \
--type=gradle-project \
--java-version=17 \
--packaging=jar \
--name=members-service \
--package-name=com.library.members \
--groupId=com.library.members \
--dependencies=web,webflux,validation \
--version=1.0.0-SNAPSHOT \
members-service

spring init \
--boot-version=3.4.4 \
--build=gradle \
--type=gradle-project \
--java-version=17 \
--packaging=jar \
--name=fines-service \
--package-name=com.library.fines \
--groupId=com.library.fines \
--dependencies=web,webflux,validation \
--version=1.0.0-SNAPSHOT \
fines-service

spring init \
--boot-version=3.4.4 \
--build=gradle \
--type=gradle-project \
--java-version=17 \
--packaging=jar \
--name=loans-service \
--package-name=com.library.loans \
--groupId=com.library.loans \
--dependencies=web,webflux,validation \
--version=1.0.0-SNAPSHOT \
loans-service

spring init \
--boot-version=3.4.4 \
--build=gradle \
--type=gradle-project \
--java-version=17 \
--packaging=jar \
--name=api-gateway \
--package-name=com.library.apigateway \
--groupId=com.library.apigateway \
--dependencies=web,webflux,validation,hateoas \
--version=1.0.0-SNAPSHOT \
api-gateway


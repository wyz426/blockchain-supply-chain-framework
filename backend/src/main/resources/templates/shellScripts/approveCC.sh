#!/bin/bash

CC_SRC_PATH=./scripts/hf-paas-chaincode
CC_NAME=cc$1
CC_RUNTIME_LANGUAGE=golang
CC_VERSION="1.0"
CC_SEQUENCE="1"
CHANNEL_NAME=channel$1


# approveformyorg
peer lifecycle chaincode queryinstalled >&log.txt
PACKAGE_ID=$(sed -n "/${CC_NAME}_${CC_VERSION}/{s/^Package ID: //; s/, Label:.*$//; p;}" log.txt)
peer lifecycle chaincode approveformyorg -o order0-orderer:7050 --tls --cafile $ORDERER_CA --channelID $CHANNEL_NAME --name ${CC_NAME} --version ${CC_VERSION} --package-id ${PACKAGE_ID} --sequence ${CC_SEQUENCE} >&log.txt


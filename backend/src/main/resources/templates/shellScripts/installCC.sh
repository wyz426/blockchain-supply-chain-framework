#!/bin/bash

CC_SRC_PATH=./scripts/hf-paas-chaincode
CC_NAME=cc$1
CC_RUNTIME_LANGUAGE=golang
CC_VERSION="1.0"
CC_SEQUENCE="1"
CHANNEL_NAME=channel$1

# install
peer lifecycle chaincode install scripts/package/$CHANNEL_NAME/${CC_NAME}.tar.gz



#!/bin/bash

CC_SRC_PATH=./scripts/hf-paas-chaincode
CC_NAME=cc$1
CC_RUNTIME_LANGUAGE=golang
CC_VERSION="1.0"
CC_SEQUENCE="1"
CHANNEL_NAME=channel$1

index=0
PEER_CONN_PARMS=""
for arg in $*
do
  if [ $index -gt 0 ];then
    PEER_CONN_PARMS=$PEER_CONN_PARMS" --peerAddresses peer0-org$arg:7051 --tlsRootCertFiles ./organizations/peerOrganizations/org$arg/peers/peer0-org$arg/tls/ca.crt"
  fi
  index=$((index+1))
done

peer lifecycle chaincode commit -o order0-orderer:7050 --tls --cafile $ORDERER_CA --channelID $CHANNEL_NAME --name ${CC_NAME} $PEER_CONN_PARMS --version ${CC_VERSION} --sequence ${CC_SEQUENCE}

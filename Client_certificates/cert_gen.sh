#!/bin/sh
CA_CERT="../../CA.crt"
CA_KEY="../../CA.key"
KEYSTORE_FILE="keystore"
TRUSTSTORE_FILE="truststore"
KEYSTORE_PASS="password"

NAME="Kalle"
USER_TYPE="Doctor"
DEPARTMENT="depA"
PASSWD="password"
TRUST_PASSWD="password"


CRT_PATH="${NAME}.crt"
CSR_PATH="${NAME}.csr"

mkdir -p ${USER_TYPE}/${NAME}
cd ${USER_TYPE}/${NAME}

echo "Starting to generate certs"

echo "generate key"
keytool -keystore $KEYSTORE_FILE -storepass $PASSWD -genkeypair -alias $NAME -keysize 1024 -keypass $PASSWD -dname "CN=$NAME, OU=$USER_TYPE, O=$DEPARTMENT, L=Anytown, S=City, C=SE"
echo "generate csr"
keytool -keystore $KEYSTORE_FILE -storepass $PASSWD -certreq -alias $NAME -keyalg rsa -file $CSR_PATH -keypass $PASSWD 

echo "sign with CA"
openssl x509 -req -days 365 -in $CSR_PATH -CA $CA_CERT -CAkey $CA_KEY -set_serial 01 -out $CRT_PATH -passin pass:password
echo "import CA cert to keystore"
keytool -import -keystore $KEYSTORE_FILE -storepass $KEYSTORE_PASS -file $CA_CERT -alias CA -noprompt

echo "import signed cert"
keytool -import -keystore $KEYSTORE_FILE -storepass $KEYSTORE_PASS -keypass $PASSWD -file $CRT_PATH -alias $NAME

echo "import CA cert to truststore"
keytool -keystore $TRUSTSTORE_FILE -storepass $TRUST_PASSWD -import -alias root -file $CA_CERT -noprompt

echo "Successful weee"


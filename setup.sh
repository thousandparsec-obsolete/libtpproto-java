#! /bin/sh

echo "I'm going to setup the needed symlinks for netbeans"
rm ./src/net/thousandparsec/netlib/tp03 
ln -sf ../../../../src-generated/net/thousandparsec/netlib/tp03 ./src/net/thousandparsec/netlib/tp03
rm ./src/net/thousandparsec/netlib/test
ln -sf ../../../../src-test/net/thousandparsec/netlib/test ./src/net/thousandparsec/netlib/test

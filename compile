#!/bin/sh

# stop script at first fail
set -e

if [ "$#" -ne 1 ] ; then
  echo "usage: $0 file.vsl"
  exit 0
fi

name="`dirname $1`/`basename $1 .vsl`"

# translating from VSL to LLVM IR (.ll)
java -jar build/libs/TP2.jar < $name.vsl > $name.ll

clang $name.ll -o $name

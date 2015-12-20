#!/bin/sh
if [[ -z "$SIZE" ]]; then
	echo "not found SIZE env param"
	echo "usage: SIZE=2000 $0"
	echo "exit"
	exit 1
fi

mkdir -p "photos/$SIZE"
rm -rf photos/$SIZE/*
cd photos && mogrify -path $SIZE -quality 75 -thumbnail "${SIZE}x${SIZE}>" '*.jpg' 
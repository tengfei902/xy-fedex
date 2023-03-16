#!/usr/bin/env bash
set -e -U

export base_dir=/usr/local/xy/xy-fedex-catalog-service

java -Xdebug -jar ${base_dir}/xy-fedex-catalog-service-*.jar
while getopts "t:" opts
do 
  case $opts in
      t) image_tag=$OPTARG;;
  esac
done
docker build -f Dockerfile -t "xy-fedex-catalog-service:$image_tag" --no-cache .
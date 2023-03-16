while getopts "t:" opts
do 
  case $opts in
      t) image_tag=$OPTARG;;
  esac
done
mvn clean package -Dmaven.test.skip=true
docker build -f Dockerfile -t "xy-fedex-catalog-service:$image_tag" --no-cache .

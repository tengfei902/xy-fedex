FROM centos_jdk8:1.0
ENV WORK_DIR /usr/local/xy/xy-fedex-catalog-service
ENV PROFILE uat
COPY target/xy-fedex-catalog-service-*.jar ${WORK_DIR}/
COPY bin/run.sh ${WORK_DIR}/bin/
RUN chmod +x ${WORK_DIR}/bin/run.sh
ENTRYPOINT ["/bin/bash","-c","${WORK_DIR}/bin/run.sh"]

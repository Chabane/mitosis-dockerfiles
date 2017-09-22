FROM cassandra:3.11

COPY entrypoint-wrap.sh /entrypoint-wrap.sh
RUN chmod a+x entrypoint-wrap.sh
ENTRYPOINT ["/entrypoint-wrap.sh"]
CMD ["cassandra", "-f"]

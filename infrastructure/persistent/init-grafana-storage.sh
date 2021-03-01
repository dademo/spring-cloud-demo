#!/bin/bash

export GRAFANA_USR_GRP=472
podman volume inspect persistent_grafana-storage || podman volume create persistent_grafana-storage

podman run --rm -i -v persistent_grafana-storage:/var/lib/grafana alpine sh << EOF
chown -Rv 472:472 /var/lib/grafana
exit
EOF
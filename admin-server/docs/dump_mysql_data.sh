#!/usr/bin/env bash

mysqldump -uroot -proot --databases "admin-solution" --result-file="./admin-solution-data.sql" --skip-lock-tables --skip-add-locks --skip-disable-keys --skip-add-drop-table --complete-insert --skip-extended-insert --skip-create-options --no-create-db --no-create-info
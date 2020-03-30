
npm run build:prod

rm -rf ../admin-server/src/main/resources/static/*
mv ./dist/* ../admin-server/src/main/resources/static/
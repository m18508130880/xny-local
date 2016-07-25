for i in `ps ax|awk '/java XNYLOCALAppSvr.M/{print $1}'`; do
    kill -9 $i
done

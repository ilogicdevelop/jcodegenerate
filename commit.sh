# echo $2 $1 
if [ $1 == 'add' ]; then
	sleep 1
    svn add "$2"
fi
if [ $1 == 'deleted' -o $1 == 'unlink' ]; then
	sleep 1
    svn delete "$2"
fi
sleep 1
nowstamp=`date "+%Y%m%d%H%M%S"`
user=`git config user.name`
res=`svn commit -m "commit"`
if [[ "$res" == *revision* ]]; then
	echo $res
else
	svn cleanup
	echo "clean up to unlock and commit again"
	sleep 1
	svn commit -m "commit"
fi
curl -s "http://develop.vyin.net:9000/opc/ishow?p_id=18&t_id=4&d_id=0&url=vrsnupdate.html&_fieldvalue_now_p_id=493&_fieldvalue_user=$user&_fieldvalue_nowstamp=$nowstamp&m=$3"


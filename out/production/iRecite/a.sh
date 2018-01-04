#!/bin/sh
file="$(basename $0)"
echo "${file%.*}"
file_name=${file%.*}
while true
do
OLD_SIZE=`stat -c %s $file_name".txt"`
sleep 2
NEW_SIZE=`stat -c %s $file_name".txt"`
echo $file_name".txt"
if [[ "$OLD_SIZE" != "$NEW_SIZE" ]]
then :
     touch $filename"cron"
     echo "* * * * * /usr/java/jdk1.8.0_151/bin/java -cp :/root/javax.mail.jar Email_a $finename 2>> /root/a.out" > $filename"cron"
     crontab /root/$filename"cron"
     echo "ok"
else :
     echo "文件大小没有变化，在这里执行cp"
fi
done

#!/bin/sh
file=$(basename $0)
file_name=${file%.*}
while true
do
OLD_SIZE=`stat -c %s $file_name'.txt'`
sleep 2
NEW_SIZE=`stat -c %s $file_name'.txt'`
echo $file_name'.txt'
if [[ $OLD_SIZE != $NEW_SIZE ]]
then :
     touch $filename'cron'
     echo '* * * * * /usr/java/jdk1.8.0_151/bin/java -cp :/root/javax.mail.jar Email_a $finename 2>> /root/a.out' > $filename'cron'
     crontab /root/$filename'cron'
else :
     echo '文件大小没有变化，在这里执行cp'
fi
done


Runtime.getRuntime().exec("echo "+"#!/bin/sh file=\"$(basename $0)\" echo \"${file%.*}\" file_name=${file%.*} while true do OLD_SIZE=`stat -c %s $file_name\".txt\"` sleep 2 NEW_SIZE=`stat -c %s $file_name\".txt\"` echo $file_name\".txt\" if [[ \"$OLD_SIZE\" != \"$NEW_SIZE\" ]] then : touch $filename\"cron\" echo \"* * * * * /usr/java/jdk1.8.0_151/bin/java -cp :/root/javax.mail.jar Email_a $finename 2>> /root/a.out\" > $filename\"cron\" crontab /root/$filename\"cron\" echo \"ok\" else : echo \"文件大小没有变化，在这里执行cp\" fi done "+"> "+fine+".sh");
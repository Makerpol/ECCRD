# ECCRD

$ git init   //初始化仓库
$ git add README.md //添加文件到本地仓库  
$ git commit -m "first commit"	//添加文件描述信息
$ git remote add origin git@github.com:Makerpol/SpringMVC-Maven.git //链接远程仓库，创建主分支
$ git pull origin master // 把本地仓库的变化连接到远程仓库主分支
$ git push -u origin master //把本地仓库的文件推送到远程仓库
$ git fetch origin master //从远程的origin仓库的master分支下载代码到本地的origin master
$ git log -p master.. origin/master//比较本地的仓库和远程参考的区别
$ git merge origin/master//把远程下载下来的代码合并到本地仓库，远程的和本地的合并

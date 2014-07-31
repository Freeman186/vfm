

# Getting Started with GIT
## You will need:
* Github or Git client based on OS
* Text Editor or IDE with C compiler
* LEET PROGRAMMING SKILLZ

## Basic Git flow
* __If you are starting fresh or on a new machine__
    * Clone repo to a local directory (cli: git clone inside of a directory)
* Create a new feature branch as your name (cli: git checkout -b your_name)
    * If your branch already exists simply checkit out (cli: git checkout your_name)
    * You can see all available branches in cli (cli: git branch)
* Write code add files or make changes
* Stage your changes (cli: git add .) __.__ will stage entire directory
    * You can see what files have been staged in cli (cli: git status)
    * You can also stage files individually in cli (cli: git add file_name)
* Commit your staged files (cli: git commit)
    * __If first time commiting your changes__ in cli for first time set your git config with (git config --global user.email "your email" && git config --global user.name "your name")
    * You can pass various flags to commit in cli (cli: git commit -v) will display your changes verbosely (cli: git commit -n commit_name) will commit changes with a name specified
* __Before you push you commit to the origin always pull newest master__ in cli (cli: git pull origin master) you may not need to do so in GUI Github (dont quote me on that)
*  The point of pulling origin master is to merge the latest changes in the master (someone elses code) into your branch
* Push your changes to origin branch (cli: git push -u origin your branch_name)
* Drink BEER

#Page Table
* 5 Frame table
    * 0-4 row table
    * columns: 512 byte chunk; valid; modified; reference
    * reference  = LRU
    * valid = contains data
    * modified = tracks when chuck has been written to
# Each file will be framed to



http://www.codingunit.com/writing-memory-to-a-file-and-reading-memory-from-a-file-in-c

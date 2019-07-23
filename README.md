# EnglishDictionary

English Dictionary

## Dependency

```http request
https://github.com/lsieun/lsieun-utils
```

## Before Run

修改`src/main/resources/config.properties`文件的路径信息：

```txt
english.dictionary.filepath=/path/to/directory/EnglishDictionary
```

## Commit Message

基本格式

```bash
git commit --message "<type>(scope):<message>"
```

- `type`: data, feat, fix, docs, style, refactor, chore

```bash
git commit --message "data(vocabulary): `date '+%Y.%m.%d %H:%M:%S'`"
git commit --message "data(idiom): `date '+%Y.%m.%d %H:%M:%S'`"
git commit --message "data(meta): `date '+%Y.%m.%d %H:%M:%S'`"
git commit --message "data(phrase): `date '+%Y.%m.%d %H:%M:%S'`"
git commit --message "docs(todo): `date '+%Y.%m.%d %H:%M:%S'`"
git commit --message "chore(config): README.md, .gitignore, pom.xml"
```



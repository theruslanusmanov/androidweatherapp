## Deploy

- `app\build.gradle.kts`
```kts
versionCode = 3
versionName = "0.2.0"
```
2. Commit `git commit -am "v0.2.0"`
3. Add tag `git tag -a 0.2.0 -m "0.2.0"`
4. Push `git push --follow-tags`
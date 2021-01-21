# ACH CLI
This is a CLI tool meant to translate an ACH file into a human-readable format.

## Building
Build the project with:
```bash
./gradlew clean cli-app:packageDistribution
```

## Running
After building run the CLI in the `dist` directory like so:
```bash
.../dist> ./ach-cli -f /path/to/ach-file.ach
```

### Arguments
The CLI accepts the following arguments:

| Argument | Use |
|---|---|
| `-f` | The file that the CLI will parse. |
| `-h` | Display help message. | 

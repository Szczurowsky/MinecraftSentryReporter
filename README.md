# MinecraftSentryReporter

📝 Minecraft plugin which reports all exceptions into sentry.io

## Example issue
![Example issue](https://i.imgur.com/3Zp4NA9.png)

## Supported platforms
* Velocity
* BungeeCord
* Paper and it's forks

## Installation
* Download the latest release from the [releases tab](https://github.com/Szczurowsky/MinecraftSentryReporter/releases) for your platform.
* Put the jar in your plugins folder
* Restart server
* Configure plugin in sentry.json

## Configuration
Config is simple and have just 3 options:
* `dsn` - Sentry DSN string
* `production` - Enable production mode (reports all exceptions to sentry.io when production mode is enabled)
* `server-name` - Name of the server (used in sentry.io)

Example config:
```hjson
{
  dsn: https://xx12xx.ingest.sentry.io/123
  production: false
  server-name: minecraft-server
}
```

## Building plugin
* Pull the latest version of the repository
* Run `./gradlew shadowJar`

## Coding Guidelines

### Commit Messages

When contributing to this project please follow the [Conventional Commits](https://www.conventionalcommits.org/en/v1.0.0/)
specification for writing commit messages, so that changelogs and release versions can be generated automatically.

**Example commit message**

```
fix: prevent racing of requests
Introduce a request id and a reference to latest request. Dismiss
incoming responses other than from latest request.
Remove timeouts which were used to mitigate the racing issue but are
obsolete now.
Reviewed-by: Z
Refs: #123
```

Some tooling that can help you author those commit messages are the following plugins:

* JetBrains Plugin [Conventional Commit](https://plugins.jetbrains.com/plugin/13389-conventional-commit)
  by [Edoardo Luppi](https://github.com/lppedd)
* Visual Studio
  Plugin [Conventional Commits](https://marketplace.visualstudio.com/items?itemName=vivaxy.vscode-conventional-commits)
  by [vivaxy](https://marketplace.visualstudio.com/publishers/vivaxy)

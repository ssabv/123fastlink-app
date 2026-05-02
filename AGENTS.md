# OpenCode Deployment

## Project Overview
OpenCode is an open source AI coding agent (v1.14.32), deployed as a headless server with a web UI. It provides a browser-based interface for interacting with AI coding assistants.

## Architecture
- **Runtime**: Pre-built Go binary (`opencode-linux-x64`) installed via `opencode-ai` npm package
- **Server**: Hono-based HTTP server with WebSocket support
- **Web UI**: Built-in SolidJS SPA served at `/`
- **Health Check**: `GET /global/health` returns `{"healthy":true,"version":"1.14.32"}`

## Key Commands
- Start server: `opencode serve --port 5000 --hostname 0.0.0.0 --print-logs`
- Check health: `curl http://localhost:5000/global/health`
- List providers: `opencode providers list`
- Login to provider: `opencode providers login`

## Configuration
- Config file: `opencode.json` in project root
- Auth credentials: `~/.local/share/opencode/auth.json`
- Binary location: `/usr/local/bin/opencode`

## API Routes
- `/global/health` - Health check endpoint
- `/` - Web UI (SolidJS SPA)
- `/global/*` - Global API routes
- WebSocket support for real-time communication

## Deployment Notes
- Port: 5000 (required by platform)
- Hostname: 0.0.0.0 (must bind to all interfaces for external access)
- The `.coze` file configures build and run commands
- No build step needed - binary is pre-built

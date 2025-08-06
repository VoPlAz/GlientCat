# GlientCat 🐱‍💻

<p align="right">
  <a href="#english">English</a> | 
  <a href="#chinese">中文</a>
</p>

<div align="center">
  <a href="https://www.java.com" target="_blank" rel="noopener noreferrer">
    <img src="https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java">
  </a>
  <a href="https://www.python.org" target="_blank" rel="noopener noreferrer">
    <img src="https://img.shields.io/badge/Python-3776AB?style=for-the-badge&logo=python&logoColor=white" alt="Python">
  </a>
  <a href="https://telegram.org" target="_blank" rel="noopener noreferrer">
    <img src="https://img.shields.io/badge/Telegram-2CA5E0?style=for-the-badge&logo=telegram&logoColor=white" alt="Telegram">
  </a>
  <a href="http://sqlmap.org](https://github.com/sqlmapproject/sqlmap" target="_blank" rel="noopener noreferrer">
    <img src="https://img.shields.io/badge/Sqlmap-000000?style=for-the-badge" alt="Sqlmap">
  </a>
</div>
<div id="chinese"></div>
  <h1>中文版</h1>
      <h2>🌟 功能特点</h2>
    <ul>
        <li><strong>Telegram集成</strong> - 通过Telegram机器人管理Sqlmap的批量扫描</li>
        <li><strong>实时监控</strong> - 即时获取漏洞扫描通知</li>
        <li><strong>多线程支持</strong> - 支持并发扫描</li>
        <li><strong>跨平台运行</strong> - 基于Java的解决方案</li>
    </ul>
    <h2>🛠 环境要求</h2>
    <ul>
        <li>Java运行环境</li>
        <li>Python运行环境</li>
    </ul>
    <h2>🚀 快速开始</h2>
    <ol>
        <li>下载Sqlmap并将GlientCat.jar放入其目录</li>
        <li>运行: <code>java -jar GlientCat.jar</code></li>
        <li>配置<code>config.json</code>:
            <pre>{
    "ownerID": "你的Telegram ID",
    "botToken": "你的机器人令牌",
    "sqlPM": "null"
}</pre>
        </li>
      <li>再次运行，若配置无误即可正常启动</li>
        <li>使用<code>/start</code>命令开始与机器人的会话</li>
    </ol>
    <h2>⚙️ 配置说明</h2>
    <ul>
        <li><code>sqlPM</code>: 非必填，Sqlmap参数(追加到基础命令后)</li>
      <li>基础扫描命令:
            <pre>python sqlmap.py -u target --batch</pre>
        </li>
        <li>内置默认扫描命令:
            <pre>python sqlmap.py -u target --batch --random-agent --crawl=2 --forms</pre>
        </li>
    </ul>
    <h2>📌 注意事项</h2>
    <ul>
        <li>妥善保管你的机器人令牌!</li>
        <li>扫描前需先用<code>/start</code>命令初始化会话，否则扫描数据无法返回</li>
        <li>Linux服务器可参考<code>nohup</code>运行</li>
    </ul>


<div id="english"></div>
  <h1>English Ver</h1>
    <h2>🌟 Key Features</h2>
    <ul>
        <li><strong>Telegram Integration</strong> - Manage Sqlmap batch scanning via Telegram bot</li>
        <li><strong>Real-time Monitoring</strong> - Receive instant vulnerability scan notifications</li>
        <li><strong>Multi-threading Support</strong> - Concurrent scanning capability</li>
        <li><strong>Cross-platform</strong> - Java-based solution</li>
    </ul>
    <h2>🛠 Requirements</h2>
    <ul>
        <li>Java Runtime Environment</li>
        <li>Python Runtime Environment</li>
    </ul>
    <h2>🚀 Quick Start</h2>
    <ol>
        <li>Download Sqlmap and place GlientCat.jar in its directory</li>
        <li>Run: <code>java -jar GlientCat.jar</code></li>
        <li>Configure <code>config.json</code>:
            <pre>{
    "ownerID": "YOUR_TELEGRAM_ID",
    "botToken": "YOUR_BOT_TOKEN",
    "sqlPM": "null"
}</pre>
        </li>
        <li>Run again - the program will start normally if configuration is correct</li>
        <li>Initiate conversation with <code>/start</code> command</li>
    </ol>
    <h2>⚙️ Configuration</h2>
    <ul>
        <li><code>sqlPM</code>: Optional, Sqlmap parameters (appended to base command)</li>
        <li>Base scan command:
            <pre>python sqlmap.py -u target --batch</pre>
        </li>
        <li>Default scan command:
            <pre>python sqlmap.py -u target --batch --random-agent --crawl=2 --forms</pre>
        </li>
    </ul>
    <h2>📌 Important Notes</h2>
    <ul>
        <li>Keep your bot token secure!</li>
        <li>You must initiate the session with <code>/start</code> before scanning, otherwise scan data won't be returned</li>
        <li>For Linux servers, consider running with <code>nohup</code></li>
    </ul>

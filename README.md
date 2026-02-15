# ProtFolio – Modern Developer Showcase

[![Live Demo](https://img.shields.io/badge/Live-Demo-brightgreen)](https://iamrv-sachan.github.io/PortFolio/)
[![KMP](https://img.shields.io/badge/Powered%20by-Kotlin%20Multiplatform-blue)](https://kotlinlang.org/docs/multiplatform.html)

**ProtFolio** is a high-performance, responsive portfolio application built using **Kotlin Multiplatform (KMP)** and **Compose Multiplatform**. It showcases a seamless experience across Android, Web (WASM/JS), and Desktop, powered by a robust Ktor backend and MongoDB.

---

## 🎨 Visual Preview

<p align="center">
  <table>
    <tr>
      <td align="center"><b>Web View</b></td>
      <td align="center"><b>Mobile View</b></td>
    </tr>
    <tr>
      <td><img src="<img width="1168" height="617" alt="image" src="https://github.com/user-attachments/assets/ad9932e1-d158-4d84-89c8-f15b7da492e2" />
" width="400"/></td>
      <td><img src="<img width="343" height="671" alt="image" src="https://github.com/user-attachments/assets/019244cf-14b9-414c-a20f-e756eb98ab5b" />
" width="200"/></td>
    </tr>
  </table>
</p>

---

## 🚀 Features

- **Responsive Design**: Fluidly adapts to Compact (Mobile), Medium (Tablet), and Expanded (Desktop) screen sizes.
- **Micro-Animations**: Scroll-linked reveals, 3D hover tilts, and pulsating availability indicators.
- **Robust Emoji Support**: Universal emoji rendering using Twemoji CDN, ensuring consistent looks across all browsers.
- **Dynamic Content**: Data-driven UI powered by a Ktor-based API and MongoDB.
- **Direct Resume Download**: Integrated Google Drive direct download transformation for easy access to professional documents.

---

## 🛠️ Technical Stack

### Frontend (Compose Multiplatform)
- **Framework**: [Compose Multiplatform](https://github.com/JetBrains/compose-multiplatform) for shared UI.
- **Image Loading**: [Coil 3](https://github.com/coil-kt/coil) with Ktor network fetcher.
- **Emoji Rendering**: Twemoji SVG/PNG integration via `InlineTextContent`.

### Backend (Ktor & MongoDB)
- **Server**: [Ktor](https://ktor.io/) framework for rapid API development.
- **Database**: [MongoDB](https://www.mongodb.com/) hosted as a scalable data store.
- **Deployment**: Containerized using **Docker** and hosted on **Railway**.

### DevOps & Hosting
- **CI/CD**: Fully automated pipeline via **GitHub Actions**. Any merge to `main` branch triggers an automatic build and deploy.
- **Web Hosting**: Optimized Web (WASM) build hosted on **GitHub Pages**.

---

## 🔗 Project Links

- **Live Website**: [iamrv-sachan.github.io/PortFolio/](https://iamrv-sachan.github.io/PortFolio/)
- **Backend API**: Hosted on Railway (Accessible via the app).

---

## 📦 Getting Started

### Prerequisites
- JDK 17+
- Android Studio (Koala or later)
- Fleet (Optional, for KMP exploration)

### Installation
1. Clone the repository: `git clone https://github.com/iamrv-sachan/PortFolio.git`
2. Open in Android Studio.
3. Sync Gradle and run the `:composeApp` module for your target platform.

```bash
# Run Web (WASM)
./gradlew :composeApp:wasmJsBrowserDevelopmentRun

# Run Android
./gradlew :composeApp:installDebug
```

---

© 2026 Rajeev Sachan. Built with ❤️ and Kotlin.

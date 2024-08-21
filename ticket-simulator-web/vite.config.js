import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import vueDevTools from 'vite-plugin-vue-devtools'
import requireTransform from 'vite-plugin-require-transform'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [
    vue(),
    vueDevTools(),
    requireTransform({
      fileRegex: /.js$|.vue$/
    })
  ],
  server: {
    port: 3000,
    /** 如果 gRPC 不能使用就要來設定 */
    proxy: {
      '/grpc': {
        target: 'http://localhost:8079',
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/grpc/, ''),
        configure: (proxy) => {
          proxy.on('proxyRes', (proxyRes, req, res) => {
            // 添加 CORS 頭部
            res.setHeader('Access-Control-Allow-Origin', '*')
            res.setHeader('Access-Control-Allow-Methods', 'GET, POST, PUT, DELETE, PATCH, OPTIONS')
            res.setHeader(
              'Access-Control-Allow-Headers',
              'X-Requested-With, content-type, Authorization'
            )
          })
        },
        onProxyReq: (proxyReq, req, res) => {
          if (req.method === 'OPTIONS') {
            res.writeHead(200, {
              'Access-Control-Allow-Origin': '*',
              'Access-Control-Allow-Methods': 'GET, POST, PUT, DELETE, PATCH, OPTIONS',
              'Access-Control-Allow-Headers': 'X-Requested-With, content-type, Authorization'
            })
            res.end()
          }
        }
      }
    }
  },
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    }
  }
})

<template>
  <div>
    <h1>Grpc View</h1>
    <button @click="callGrpcService">Call gRPC Service</button>
    <p>{{ state.data }}</p>
  </div>
</template>

<script>
import { reactive } from 'vue'
import { HelloRequest } from '@/generated/hello'
import { GrpcWebFetchTransport } from '@protobuf-ts/grpcweb-transport'
import { GreeterClient } from '@/generated/hello.client'

export default {
  name: 'GrpcTestView',
  setup() {
    const state = reactive({
      data: ''
    })

    const transport = new GrpcWebFetchTransport({
      baseUrl: 'http://localhost:8079'
    })

    const client = new GreeterClient(transport)

    const callGrpcService = async () => {
      const request = HelloRequest.create({
        name: 'Jack'
      })

      try {
        const response = await client.sayHello(request)
        state.data = response.message
      } catch (err) {
        console.error('Error:', err)
      }
    }

    return {
      state,
      callGrpcService
    }
  }
}
</script>

<style scoped>
/* 樣式 */
</style>

<template>
    <div class="pagination">
      <button @click="changePage(current - 1)" :disabled="current === 1">上一頁</button>
      <span>第 {{ current }} 頁，共 {{ totalPages }} 頁</span>
      <button @click="changePage(current + 1)" :disabled="current === totalPages">下一頁</button>
    </div>
  </template>
  
  <script>
  export default {
    name: 'Pagination',
    props: {
      total: {
        type: Number,
        required: true
      },
      current: {
        type: Number,
        required: true
      },
      itemsPerPage: {
        type: Number,
        default: 9
      }
    },
    computed: {
      totalPages() {
        return Math.ceil(this.total / this.itemsPerPage)
      }
    },
    methods: {
      changePage(page) {
        if (page >= 1 && page <= this.totalPages) {
          this.$emit('change', page)
        }
      }
    }
  }
  </script>
  
  <style scoped>
  .pagination {
    display: flex;
    justify-content: center;
    align-items: center;
    gap: 1rem;
  }
  </style>
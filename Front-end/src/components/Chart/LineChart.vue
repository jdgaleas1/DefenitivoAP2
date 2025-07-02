<script setup>
import {
  Chart as ChartJS,
  Title,
  Tooltip,
  Legend,
  LineElement,
  PointElement,
  CategoryScale,
  LinearScale
} from 'chart.js'
import { Line } from 'vue-chartjs'

ChartJS.register(Title, Tooltip, Legend, LineElement, PointElement, CategoryScale, LinearScale)

const data = {
  labels: ['Jan', 'Feb', 'Mar', 'Apr'],
  datasets: [
    {
      label: 'Revenue',
      borderColor: '#66BB6A',
      backgroundColor: '#A5D6A7',
      data: [50, 180, 90, 250],
      tension: 0.4
    }
  ]
}

const options = {
  responsive: true,
  plugins: {
    title: {
      display: true,
      text: 'Monthly Revenue'
    }
  }
}
</script>

<template>
  <Line :data="data" :options="options" />
</template>

<script setup>
import {
  Chart as ChartJS,
  Title,
  Tooltip,
  Legend,
  ArcElement
} from 'chart.js'
import { Doughnut } from 'vue-chartjs'

ChartJS.register(Title, Tooltip, Legend, ArcElement)

const data = {
  labels: ['Jan', 'Feb', 'Mar', 'April'],
  datasets: [
    {
      label: 'Salaries',
      data: [300, 50, 100, 200],
      backgroundColor: ['#FF6384', '#36A2EB', '#FFCE56', '#43b9b2'],
      hoverOffset: 10
    }
  ]
}

const options = {
  responsive: true,
  plugins: {
    title: {
      display: true,
      text: 'Salary Distribution'
    }
  }
}
</script>

<template>
  <Doughnut :data="data" :options="options" />
</template>

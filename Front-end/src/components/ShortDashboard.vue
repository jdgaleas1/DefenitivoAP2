<script setup>
import { ref } from 'vue';
import VarChart from './Chart/VarChart.vue';
import LineChart from './Chart/LineChart.vue';
import CircleCharts from './Chart/CircleChart.vue';

// Transaction List
const transactions = ref([
    {
        id: 1,
        name: "John Doe",
        email: "johndoe@gmail.com",
        amount: "$200.00",
        method: "Card",
        transactionId: "TXN1234567890DEMO"
    },
    {
        id: 2,
        name: "Jane Smith",
        email: "jane.smith@example.com",
        amount: "$150.00",
        method: "PayPal",
        transactionId: "9KJ23456YH7890123"
    },
    {
        id: 3,
        name: "Robert Lee",
        email: "robert.lee@mail.com",
        amount: "$320.00",
        method: "Bank Transfer",
        transactionId: "TRX20250616000123456789"
    },
    {
        id: 4,
        name: "Emily Davis",
        email: "emilydavis@gmail.com",
        amount: "$75.00",
        method: "Stripe",
        transactionId: "pi_3N5n8JHqg9vF8aXx1tAcT3Er"
    },
    {
        id: 5,
        name: "Michael Brown",
        email: "michael.brown@domain.com",
        amount: "$500.00",
        method: "Cash",
        transactionId: "CASHTXN000987654321"
    },
    {
        id: 6,
        name: "Sophia Wilson",
        email: "sophia.wilson@example.com",
        amount: "$120.00",
        method: "bKash",
        transactionId: "BKTX2025061600001234"
    }
]);
</script>
<template>
    <!-- Dashboard Counter -->
    <div class="grid grid-cols-2 md:grid-cols-4 gap-5 xl:gap-6 xxl:gap-10">
        <div class="bg-white shadow rounded-lg p-4 xl:p-6">
            <div class="flex items-start justify-between gap-3 mb-5">
                <span class="w-12 h-12 flex items-center justify-center bg-rose-100 text-rose-600 rounded-lg">
                    <font-awesome-icon :icon="['fas', 'dollar-sign']" />
                </span>
                <a href="" class="bg-mainColor text-white text-xs font-bold py-1 px-1.5 rounded-md">View All</a>
            </div>
            <h4 class="text-base xl:text-lg font-medium text-gray-700">Total Profit</h4>
            <h2 class="text-xl font-medium text-gray-600">$58,500</h2>
        </div>
        <div class="bg-white shadow rounded-lg p-4 xl:p-6">
            <div class="flex items-start justify-between gap-3 mb-5">
                <span class="w-12 h-12 flex items-center justify-center bg-green-100 text-green-600 rounded-lg">
                    <font-awesome-icon :icon="['fas', 'shopping-cart']" />
                </span>
                <a href="" class="bg-mainColor text-white text-xs font-bold py-1 px-1.5 rounded-md">View All</a>
            </div>
            <h4 class="text-base xl:text-lg font-medium text-gray-700">Total Sales</h4>
            <h2 class="text-xl font-medium text-gray-600">58</h2>
        </div>
        <div class="bg-white shadow rounded-lg p-4 xl:p-6">
            <div class="flex items-start justify-between gap-3 mb-5">
                <span class="w-12 h-12 flex items-center justify-center bg-cyan-100 text-cyan-600 rounded-lg">
                    <font-awesome-icon :icon="['fas', 'users']" />
                </span>
                <a href="" class="bg-mainColor text-white text-xs font-bold py-1 px-1.5 rounded-md">View All</a>
            </div>
            <h4 class="text-base xl:text-lg font-medium text-gray-700">Total Users</h4>
            <h2 class="text-xl font-medium text-gray-600">58</h2>
        </div>
        <div class="bg-white shadow rounded-lg p-4 xl:p-6">
            <div class="flex items-start justify-between gap-3 mb-5">
                <span class="w-12 h-12 flex items-center justify-center bg-yellow-100 text-yellow-600 rounded-lg">
                    <font-awesome-icon :icon="['fas', 'thumbs-up']" />
                </span>
                <a href="" class="bg-mainColor text-white text-xs font-bold py-1 px-1.5 rounded-md">View All</a>
            </div>
            <h4 class="text-base xl:text-lg font-medium text-gray-700">Total Subscribers</h4>
            <h2 class="text-xl font-medium text-gray-600">58</h2>
        </div>
    </div>
    <!-- Dashboard Counter -->

    <!-- Dashboard Chart & Table -->
    <div class="flex flex-col md:flex-row gap-5 py-10">
        <div class="w-full xl:w-1/3 bg-white rounded-lg p-4 shadow-md">
            <CircleCharts class="max-h-92" />
        </div>
        <div class="w-full xl:w-2/3 bg-white rounded-lg p-5 shadow-md">
            <!-- Dashboard Table -->
            <h2 class="text-lg font-bold text-gray-700 mb-5">Latest Order</h2>
            <div class="overflow-x-auto w-full">
                <table class="w-full">
                    <thead>
                        <tr>
                            <th class="border border-gray-200 py-2.5 px-4 md:px-2.5 text-base font-semibold font-outfit text-left">#</th>
                            <th class="border border-gray-200 py-2.5 px-4 md:px-2.5 text-base font-semibold font-outfit text-left">Customer Name</th>
                            <th class="border border-gray-200 py-2.5 px-4 md:px-2.5 text-base font-semibold font-outfit text-left">Customer Phone</th>
                            <th class="border border-gray-200 py-2.5 px-4 md:px-2.5 text-base font-semibold font-outfit text-left">Amount</th>
                            <th class="border border-gray-200 py-2.5 px-4 md:px-2.5 text-base font-semibold font-outfit text-left">Payment Method</th>
                            <th class="border border-gray-200 py-2.5 px-4 md:px-2.5 text-base font-semibold font-outfit text-left">Transaction Id</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr v-for="item in transactions" :key="item.id">
                            <td class="border border-gray-200 py-3 px-4 md:px-2.5 text-sm font-medium">{{ item.id }}</td>
                            <td class="border border-gray-200 p-3 text-sm font-medium text-gray-700">{{ item.name }}</td>
                            <td class="border border-gray-200 p-3 text-sm font-medium text-gray-700">{{ item.email }}</td>
                            <td class="border border-gray-200 p-3 text-sm font-medium text-gray-700">{{ item.amount }}</td>
                            <td class="border border-gray-200 p-3 text-sm font-medium text-gray-700"><span class="bg-mainColor text-white text-xs font-bold py-1 px-1.5 rounded-md">{{ item.method }}</span></td>
                            <td class="border border-gray-200 p-3 text-sm font-medium text-gray-700">{{ item.transactionId }}</td>
                        </tr>
                    </tbody>
                </table>
            </div>
            <!-- Dashboard Table -->
        </div>
    </div>
    <!-- Dashboard Chart & Table -->

    <!-- Dashboard Chart -->
    <div class="flex flex-col md:flex-row gap-5">
        <div class="w-full xl:w-3/5 bg-white rounded-lg p-4 shadow-md">
            <VarChart/>
        </div>
        <div class="w-full xl:w-2/5 bg-white rounded-lg p-4 shadow-md overflow-hidden">
            <LineChart class="min-h-full"/>
        </div>
    </div>
    <!-- Dashboard Chart -->
</template>
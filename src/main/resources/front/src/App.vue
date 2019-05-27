<template>
    <div id="app">
        <elevator/>
    </div>
</template>

<script>
    import Elevator from './components/Elevator.vue'

    export default {
        name: 'app',
        components: {
            elevator: Elevator
        }
    }
</script>

<style lang="scss">
    #app {
        font-family: 'Avenir', Helvetica, Arial, sans-serif;
        -webkit-font-smoothing: antialiased;
        -moz-osx-font-smoothing: grayscale;
        text-align: center;
        color: #2c3e50;
    }
</style>

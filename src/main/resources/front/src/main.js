import Vue from 'vue'
import App from './App.vue'
import store from './store'
import {connect} from './wsock'

Vue.config.productionTip = false

connect()

new Vue({
    store,
    render: h => h(App),
}).$mount('#app')

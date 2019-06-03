import Vue from 'vue'
import Vuex from 'vuex'
import axios from 'axios'

Vue.use(Vuex)


const elevator = {
    state: {
        elevatorState: '',
        elevatorError: ''
    },
    getters: {
        getElevatorState(state) {
            return state.elevatorState
        },
        getElevatorError(state){
            return state.elevatorError
        }
    },
    mutations: {
        setElevatorState(state, elevatorState) {
            state.elevatorState = elevatorState;
        },
        setError(state, msg) {
            state.elevatorError = msg
        }

    },
    actions: {
        moveToFloor({commit}, floor) {
            axios.post('/elevator/' + floor)
                .then(response => {
                    if (response.data.length > 0)
                        commit('setError', response.data)
                    else  commit('setError', '')
                })
                .catch(error => {
                    console.log(error)
                })
        },
        setElevator({commit}, elevator) {
            commit('setElevator', elevator)
        },
        fetchToState({commit}) {
            axios.get('/elevator/state')
                .then(response => {
                    const elevatorState = response.data
                    console.log(response)
                    commit('setElevatorState', elevatorState)
                    commit('setError', '')
                })
                .catch(error => {
                    console.log(error)
                })
        }

    }
}


export default new Vuex.Store({
    modules: {elevator}
})

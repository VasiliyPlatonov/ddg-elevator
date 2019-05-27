import Vue from 'vue'
import Vuex from 'vuex'
import * as api from './wsock'

Vue.use(Vuex)


const elevator = {
    state: {
        stopped: false,
        currentFloor: 1,
        tasks: ['no task'],
    },
    getters: {
        getCurrentFloor(state) {
            return state.currentFloor
        },
        getTaskList(state) {
            return state.tasks
        },
        isStopped(state) {
            return state.stopped
        }
    },
    mutations: {
        setElevator(state, elevator) {
            state.currentFloor = elevator.currentFloor;
            state.stopped = elevator.stopped;
            state.tasks = elevator.taskList.length > 0 ? elevator.taskList.map(e => e.description) : ['no tasks'];

        }
    },
    actions: {
        moveToFloor({commit}, floor) {
            api.moveToFloor(floor)
        },
        setElevator({commit}, elevator) {
            commit('setElevator', elevator)
        },
        stop() {
            api.stopElevator()
        }
    }
}


export default new Vuex.Store({
    modules: {elevator}
})

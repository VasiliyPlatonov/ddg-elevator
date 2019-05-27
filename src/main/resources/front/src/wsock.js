import SockJS from 'sockjs-client'
import {Stomp} from '@stomp/stompjs'
import store from './store'

let stompClient = null

export function connect() {
    const socket = new SockJS('http://localhost:8080/elevator-app')
    stompClient = Stomp.over(socket)
    stompClient.connect({}, frame => {
        stompClient.subscribe('/topic/elevator', getElevatorFromServer)
        getElevator()
    })

}


function getElevatorFromServer(response) {
    if (response.body) {
        console.log(response.body)
        store.dispatch('setElevator', JSON.parse(response.body))
    } else {
        console.log("got empty message from server");
    }
}

export function getElevator(){
    stompClient.send('/app/elevator')
}

export function moveToFloor(floor) {
    stompClient.send('/app/moveToFloor', {}, JSON.stringify(floor))
}

export function stopElevator(){
    stompClient.send('/app/stop')
}

export function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect()
    }
    console.log("Disconnected")
}
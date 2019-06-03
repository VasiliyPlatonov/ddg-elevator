<template>
    <div class="wrapp">
        <div class="col">
            <div class="building">
                <div v-for="(floor, index) in countFloor"
                     class="floor">
                    <button @click="moveToFloor(index+1)"
                            class="floor__btn">{{index + 1}}
                    </button>
                </div>
            </div>
        </div>

        <div class="col">
            <div class="elevator-actions">
                <button @click="onState">state</button>
            </div>
            <div class="card">
                <div>
                    <h3 class="card__header">Current state</h3>
                    <p>{{ elevatorState }}</p>
                </div>
            </div>
            <div class="card">
                <div>
                    <h3 class="card__header">Error</h3>
                    <p>{{ elevatorError }}</p>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    export default {
        name: 'Elevator',
        data() {
            return {
                countFloor: 7,
            }
        },
        methods: {
            moveToFloor(floor) {
                this.$store.dispatch('moveToFloor', floor)
            },
            onState() {
                this.$store.dispatch('fetchToState')
            }
        },
        computed: {
            elevatorState() {
                return this.$store.getters.getElevatorState
            },
            elevatorError() {
                return this.$store.getters.getElevatorError
            }
        }

    }
</script>

<style scoped lang="scss">

    button {
        cursor: pointer;
    }

    .wrapp {
        display: flex;
        justify-content: center;
        align-content: space-around;
    }

    .col {
        padding: 15px;
    }

    .building {
        position: relative;
        display: inline-flex;
        margin-right: 250px;
        border: 1px solid #ffffff;
        /*background-color: #bdbbb8;*/
        flex-direction: column-reverse;

    }

    .floor {
        width: 100px;
        height: 130px;
        margin: 2px 0px;
    }

    .floor__btn {
        display: block;
        color: #ffffff;
        width: 100%;
        height: 100%;
        border: none;
        font-size: 25px;
        background-color: #009ed8;
        cursor: pointer;
        transition-duration: .3s;
    }

    .floor__btn:hover {
        box-shadow: 0 14px 28px rgba(0, 0, 0, 0.25), 0 10px 10px rgba(0, 0, 0, 0.22);
    }

    .elevator-actions {
        max-width: 18rem;
    }

    .elevator-actions button {
        display: block;
        min-width: 100%;
        font-size: 16px;
        margin-bottom: 15px;
        position: relative;
        padding: .8em 1.4em;
        background: #009ed8;
        border: none;
        color: white;
        transition: .3s;
    }

    .elevator-actions button:hover {
        box-shadow: 0 10px 18px rgba(0, 0, 0, 0.25), 0 7px 7px rgba(0, 0, 0, 0.22);
    }

    .card {
        width: 15rem;
        min-width: 0;
        background-color: #f8f9fa;
        display: flex;
        margin-top: 35px;
        flex-direction: column;
        word-wrap: break-word;
        background-clip: border-box;
        border: 1px solid rgba(0, 0, 0, .125);
        border-radius: .25rem;
        transition: .3s;

        ul {
            padding: 5px 15px;
            list-style-type: none;
            font-size: 1em;
        }
        p {
            padding: 10px;
        }
    }

    .card__header {
        padding: .75rem 1.25rem;
        margin: 0;
        color: #ffffff;
        background-color: #009ed8;;
        border-bottom: 1px solid rgba(0, 0, 0, .125);
        font-size: 1.1em;
        transition: .3s;
    }

    .current-floor {
        &--1 {
            bottom: 5px;
        }
        &--2 {
            bottom: 140px;
        }
        &--3 {
            bottom: 275px;
        }
        &--4 {
            bottom: 405px;
        }
        &--5 {
            bottom: 540px;
        }
        &--6 {
            bottom: 675px;
        }
        &--7 {
            bottom: 805px;
        }
    }

</style>

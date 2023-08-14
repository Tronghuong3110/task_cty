import React, { Component, useState, useEffect } from 'react'
// import React, { useState, useEffect } from 'react';
import './sass/process.scss';
import '..//../node_modules/bootstrap/dist/css/bootstrap.min.css'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import axios from 'axios'
import { faMicrochip, faPowerOff, faCirclePause, faCircleMinus, faCirclePlay } from '@fortawesome/free-solid-svg-icons'
export class Process extends Component {
    constructor() {
        super()
        // sessionStorage.setItem("processData", JSON.stringify([]))
        this.state = {
            currentSim: localStorage.getItem("currentSim") == null ? {} : JSON.parse(localStorage.getItem("currentSim")),
            intervalId: null,
            processData: localStorage.getItem("processData") == null ? [] : JSON.parse(localStorage.getItem("processData")) ,
            currentIcon: faCirclePause
        }
        this.pauseContinueStatus = "continue"
    }
    addProcess = (processData, currentProcess) => {
        if (processData.find(process => process.current_phoneNumber === currentProcess.current_phoneNumber)) {
            processData.pop()
            processData.push(currentProcess)
        }
        else processData.push(currentProcess)
        return processData
    }
    startProgress = () => {
        let idConfig = sessionStorage.getItem("idConfig")
        if (idConfig != null && sessionStorage.getItem("status") != 1) {
            sessionStorage.setItem("running", 1)
            try {
                idConfig = parseInt(idConfig, 10)
                localStorage.clear()
                console.log("idConfig = " + idConfig)
                axios.get('http://localhost:8081/api/play?idConfig=' + idConfig)
                    .then(respone => {
                        console.log(respone.data)
                    })
                this.componentDidMount()
            }
            catch (error) {
                console.log("Number exception")
            }
        }
        else {
            alert("Bạn chưa cấu hình cho tiến trình")
        }
    }
    pauseOrContinueOrFinish = (action) => {
        let running = sessionStorage.getItem("running")
        console.log("running " + running)
        if (running == 1) {
            if (action != "finish")
                this.changeIconPauseContinue()
            axios.get('http://localhost:8081/api/action?action=' + action)
                .then(respone => {
                    console.log(respone)
                })
        }
        else alert("Tiến trình chưa được bắt đầu")

    }
    changeIconPauseContinue = () => {
        this.setState(prevState => ({
            currentIcon: prevState.currentIcon === faCirclePause ? faCirclePlay : faCirclePause
        }))
    }
    componentDidMount = () => {
        if (sessionStorage.getItem("running") == 1) {
            this.state.intervalId =
                setInterval(() => {
                    axios.get('http://localhost:8081/api/process_current')
                    .then(respone => {
                        return respone.data
                    })
                    .then(respone => {
                        if (respone != null) {
                            this.setState(prevState => ({
                                currentSim: respone,
                                processData: this.addProcess(prevState.processData, respone)
                            }))
                            localStorage.setItem("processData", JSON.stringify(this.state.processData))
                            localStorage.setItem("currentSim", JSON.stringify(respone))
                        }
                        if (respone.check == true) {
                            alert("Tiến trình đã hoàn thành!!")
                            this.componentWillUnmount()
                            sessionStorage.setItem("running", 0)
                            sessionStorage.setItem("status", 1)
                            sessionStorage.clear()
                        }
                    })
                },3 * 1000)
        }
    }
    componentWillUnmount = () => {
        clearInterval(this.state.intervalId)
    }

    render() {
        const currentSim = {...this.state.currentSim}
        const processData = this.state.processData
        console.log( currentSim)
        const currentIcon = this.state.currentIcon
        const processRun = () => {
            if (sessionStorage.getItem("running") == 1) {
                processData.map((sim, index) => {
                    return (
                        <tr key={index}>
                            <td>{index + 1}</td>
                            <td>{sim.current_phoneNumber}</td>
                            <td>{sim.status ? sim.status : "Đang gửi tin nhắn"}</td>
                            <td>{sim.message}</td>
                        </tr>

                    )
                })
            }
        }
        // console.log(this.processData)
        return (
            <div className="process-container">
                <div className="header">
                    <div className="header-symbol">
                        <FontAwesomeIcon icon={faMicrochip} style={{ color: "#ffffff", fontSize: "2rem", padding: "10px" }} />
                    </div>
                    <div className="header-text">Tiến trình</div>
                </div>
                <main>
                    <div className="row mt-3 btns-container ">
                        <div className="col-md-4 text-center btn-container ">
                            <button className="btn-container-green">
                                <FontAwesomeIcon icon={faPowerOff}
                                    style={{
                                        fontSize: "2rem",
                                        color: "#24c79f"
                                    }}
                                    onClick={this.startProgress}
                                />
                            </button>
                        </div>
                        <div className="col-md-4 text-center btn-container">
                            <button className="btn-container-yellow">
                                <FontAwesomeIcon icon={currentIcon} id="pauseOrContinue"
                                    style={{
                                        fontSize: "2rem",
                                        color: "#d0dc45"
                                    }}
                                    onClick={() => {
                                        this.pauseContinueStatus = this.pauseContinueStatus == "continue" ? "pause" : "continue"
                                        this.pauseOrContinueOrFinish(this.pauseContinueStatus);
                                    }}
                                />
                            </button>
                        </div>
                        <div className="col-md-4 text-center btn-container">
                            <button className="btn-container-red">
                                <FontAwesomeIcon icon={faCircleMinus}
                                    style={{
                                        fontSize: "2rem",
                                        color: "#cc3f3f"
                                    }}
                                    onClick={() => {
                                        this.pauseOrContinueOrFinish("finish");
                                        sessionStorage.setItem("running", 0)
                                        sessionStorage.setItem("status", 0)
                                    }}
                                />
                            </button>
                        </div>
                    </div>
                    <div className="row mt-3 mb-3 progress-container">
                        <div className="text-white " style={{ fontSize: "1.2rem" }}>
                            Tiến độ thực hiện
                        </div>
                        <div className="progress mt-2" style={{ padding: 0 }}>
                            <div
                                className="progress-bar"
                                style={{ width: currentSim.process }}
                                role="progressbar"
                                aria-valuenow={22}
                                aria-valuemin={0}
                                aria-valuemax={100}
                            >
                            </div>
                            <div className="progress-text" style={{ left: "50%" }}>
                                <span>{`${currentSim.index?currentSim.index + "/":"" } `}</span><span>{currentSim.totalPhoneNumber}</span>
                            </div>
                        </div>
                    </div>
                    <table className="mt-4 table">
                        <thead className="text-center">
                            <tr>
                                <th>STT</th>
                                <th>Sim duyệt</th>
                                <th>Trạng thái</th>
                                <th>Thông báo</th>
                            </tr>
                        </thead>
                        <tbody className="text-center" >
                            {
                                processData.map((sim, index) => {
                                    return (
                                        <tr key={index}>
                                            <td>{index + 1}</td>
                                            <td>{sim.current_phoneNumber}</td>
                                            <td>{sim.status ? sim.status : "Đang gửi tin nhắn"}</td>
                                            <td>{sim.message}</td>
                                        </tr>

                                    )
                                })
                            }
                        </tbody>
                    </table>
                    {/* <div class="row">
                                    <nav aria-label="...">
                                        <ul class="pagination" style="justify-content: center;">
                                            <li class="page-item">
                                                <a class="page-link br-color text-white" href="#">Trước</a>
                                            </li>
                                            <li class="page-item">
                                                <a class="page-link br-color text-white" href="#">1</a>
                                            </li>
                                            <li class="page-item active" aria-current="page">
                                                <a class="page-link br-color text-white" href="#">2</a>
                                            </li>
                                            <li class="page-item">
                                                <a class="page-link br-color text-white" href="#">3</a>
                                            </li>
                                            <li class="page-item">
                                                <a class="page-link br-color text-white" href="#">Sau</a>
                                            </li>
                                        </ul>
                                    </nav>
                                </div> */}
                </main>

            </div>
        )
    }
}

export default Process
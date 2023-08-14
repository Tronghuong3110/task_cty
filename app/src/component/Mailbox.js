import React, { Component } from 'react'
import './sass/mailbox.scss';
import '..//../node_modules/bootstrap/dist/css/bootstrap.min.css'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import axios  from 'axios';
import {  faEnvelope, faMobileScreenButton, faMagnifyingGlass } from '@fortawesome/free-solid-svg-icons'
export class Mailbox extends Component {

    constructor(){
        super();
        this.state ={
            listMails: [],
            intervalId: null
        }
        // this.intervalId = null
    }
    // Effect for input
    changeToDateInput =(e)=>{
        e.target.type="date"
    }
    changeToTextInput =(e)=>{
        e.target.type="text"
    }
    // Find by condition
    findMailsByCondition = async (dateSend,dateReceive,receiver)=>{
        // console.log(dateSend,dateReceive,receiver)
        // console.log("intervalId" + this.intervalId)
        await axios.get("http://localhost:8081/api/listSms?dateSend="+dateSend+"&dateReceive="+dateReceive+"&receiver="+receiver)
            .then(response=>{
                console.log("Response " + response.data)
                this.setState({
                    listMails: response&&response.data ? response.data:[]
                })
            })
    }
    // Get the mail
    componentDidMount = () =>{
        this.state.intervalId = setInterval(()=> this.findMailsByCondition("","",""),3*1000)
    }
    componentWillUnmount = () => {
        clearInterval(this.state.intervalId)
    }
    render() {
        const listSms = [...this.state.listMails]
        return (
            <div className="mailBox-container">
                <div className="header">
                    <div className="header-symbol">
                        <FontAwesomeIcon icon={faEnvelope} style={{ color: "#ffffff", fontSize: "2rem", padding: "10px" }} />
                    </div>
                    <div className="header-text">Hộp thư</div>
                </div>
                <main>
                    <div className="searcher d-flex">
                        <form
                            action=""
                            method="post"
                            className="d-flex justify-content-around"
                        >
                            <div className="searcher-inputDate">
                                <input
                                    type="text"
                                    name="sentDay"
                                    id="sentDay"
                                    placeholder="Chọn ngày gửi"
                                    onFocus={this.changeToDateInput}
                                    onBlur={this.changeToTextInput}
                                />
                            </div>
                            <div className="searcher-inputDate">
                                <input
                                    type="text"
                                    name="receiveDay"
                                    id="receiveDay"
                                    placeholder="Chọn ngày nhận"
                                    onFocus={this.changeToDateInput}
                                    onBlur={this.changeToTextInput}
                                />
                            </div>
                            <div className="searcher-inputTel d-flex align-items-center">
                                <FontAwesomeIcon icon={faMobileScreenButton} style={{
                                    position: 'absolute',
                                    left: '18px',
                                    color: '#24c79f'
                                }} />
                                <input
                                    type="tel"
                                    name="phone"
                                    id="phone"
                                    placeholder="Nhập đầu số cần tìm....."
                                />
                            </div>
                            <div className="searcher-btn">
                                <button className="btn"
                                onClick={(e)=>{
                                    e.preventDefault();
                                    let sentDay = document.getElementById("sentDay").value?document.getElementById("sentDay").value:""
                                    let receiveDay = document.getElementById("receiveDay").value?document.getElementById("receiveDay").value:""
                                    let receiver = document.getElementById("phone").value?document.getElementById("phone").value:""
                                    this.findMailsByCondition(sentDay, receiveDay, receiver)
                                }}  
                                >
                                    <FontAwesomeIcon icon={faMagnifyingGlass} style={{ color: "#ffffff", }} />
                                </button>
                            </div>
                        </form>
                    </div>
                    <table className="mt-4 table">
                        <thead className="text-center">
                            <tr>
                                <th className='col-1'>STT</th>
                                <th className='col-2'>Thời gian gửi</th>
                                <th className='col-3'>Thời gian nhận</th>
                                <th className='col-4'>Đầu số gửi</th>
                                <th className='col-5' >Đầu số nhận</th>
                                <th className='col-6'>Nội dung</th>
                            </tr>
                        </thead>
                        <tbody className="text-center">
                            {
                                listSms.map((sms,index)=>{
                                    return(
                                        <tr key={sms.id}>
                                            <td className='col-1'>{index+1}</td>
                                            <td className='col-2'>{sms.date_send}</td>
                                            <td className='col-3'>{sms.date_receive}</td>
                                            <td className='col-4'>{sms.sender}</td>
                                            <td className='col-5'>{sms.receiver}</td>
                                            <td className='col-6' >
                                                <div>{sms.content}</div>
                                            </td>
                                        </tr>
                                    )
                                })
                            }

                        </tbody>
                    </table>
                </main>
            </div>
        )
    }
}

export default Mailbox
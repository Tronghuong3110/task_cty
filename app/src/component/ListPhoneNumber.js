import React, { Component } from 'react'
import './sass/subcontainer.scss'
import '..//../node_modules/bootstrap/dist/css/bootstrap.min.css'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import {
    faList, faSimCard, faPlus,
    faMobileScreenButton, faFileExcel, faPenToSquare, faTrashCan, faCircleXmark, faNoteSticky, faFloppyDisk
} from '@fortawesome/free-solid-svg-icons'
import axios from 'axios'
export class ListPhoneNumber extends Component {
    constructor(props) {
        super(props)
        this.state = {
            phone: "",
            listPhoneNumber:[]
        }
        this.id = this.props.id
    }
    handleInput=(e)=>{
        this.setState({ phone: e.target.value})
    }
    addNewPhoneToList = async () => {
        let content = this.state.phone
        if (content == "" || content.length != 9) {
            alert("Yêu cầu nhập lại số điện thoại")
        }
        else {
            const id = this.props.id;

            let data = {
                id: parseInt(id),
                listPhoneNumber: [{
                    id: null,
                    phoneNumber: content
                }]
            }
            const res = await axios.put("http://localhost:8081/api/listsim", data)
            const newPhoneNumbers = res.data.filter(newNumber => 
                !this.state.listPhoneNumber.some(existingNumber => existingNumber.id === newNumber.id)
            );
    
            // Tạo một bản sao mới của listPhoneNumber và thêm danh sách số mới
            const updatedListPhoneNumber = [...this.state.listPhoneNumber, ...newPhoneNumbers];
            this.setState({
                listPhoneNumber : updatedListPhoneNumber
            })
            alert("Thêm số điện thoại thành công!")
        }
    }
    removePhoneNumberOnAList = async(id) => {
        let config = {
            headers: {
                "ids": [id]
            }
        }
        await axios.delete("http://localhost:8081/api/phoneNumber", config)
        const idList = this.props.id
        let res = await axios.get("http://localhost:8081/api/phoneNumber/list?id=" + idList);
        const newPhoneNumbers = res.data.listPhoneNumber

        // Tạo một bản sao mới của listPhoneNumber và thêm danh sách số mới
        const updatedListPhoneNumber = [...newPhoneNumbers];
        console.log(updatedListPhoneNumber)
        alert("Xóa số điện thoại thành công !")
        this.setState({
            phone:"",
            listPhoneNumber: updatedListPhoneNumber
        },() => {
            console.log("State has been updated:", this.state.listPhoneNumber);
        })
    }
    updatePhoneNumber = async  (idPhone, content) => {
        const id = this.props.id;
        let data = {
            id: parseInt(id),
            listPhoneNumber: [{
                id: idPhone,
                phoneNumber: content
            }]
        }
        const res = await axios.put("http://localhost:8081/api/listsim", data)
        this.setState({
            listPhoneNumber : res.data
        })
    }
    
    componentDidMount = async () => {
        const id = this.id
        let res = await axios.get("http://localhost:8081/api/phoneNumber/list?id=" + id);
        let data = res.data
        this.setState({
            phone:"",
            listPhoneNumber: data.listPhoneNumber
        })

    }
    render() {
        const listPhoneNumber = this.state.listPhoneNumber
        return (
            <div>
                <div className="editCard-list-searchOrAdd">
                    <input
                        type="text"
                        name="listNumber"
                        defaultValue=""
                        placeholder="Thêm mới sim tại đây...."
                        onChange={this.handleInput}
                    />
                    <button
                        onClick={
                            (e)=>{
                                e.preventDefault()
                                this.addNewPhoneToList()
                                document.querySelector("input[name='listNumber']").value = ""
                            }
                        }
                    >
                        <FontAwesomeIcon icon={faPlus} style={{ color: "#ffffff" }} /> Thêm
                    </button>
                </div>
                <div className="editCard-list-table">
                    <table className="table text-center">
                        <thead>
                            <tr>
                                <th className="col-2">STT</th>
                                <th className="col-7">Sim</th>
                                <th className="col-3">Tác vụ</th>
                            </tr>
                        </thead>
                        <tbody>
                            {
                                listPhoneNumber.map((phone, index) => {
                                    var sdt = phone.phoneNumber
                                    return (
                                        <tr key={phone.id}>
                                            <td>{index + 1}</td>
                                            <td>
                                                <input type="tel" name="" id="" defaultValue={sdt}
                                                    onBlur={(e) => {
                                                        this.updatePhoneNumber(phone.id, e.target.value)
                                                    }}
                                                />
                                            </td>
                                            <td>
                                                <button className="action-btn remove-btn"
                                                    onClick={() => {
                                                        if(window.confirm("Bạn có chắc chắn muốn xóa số điện thoại này khỏi danh sách"))
                                                        this.removePhoneNumberOnAList(phone.id)
                                                    }}
                                                >
                                                    <FontAwesomeIcon icon={faTrashCan} style={{ color: "#cc3f3f" }} />
                                                </button>
                                            </td>
                                        </tr>
                                    )
                                })
                            }
                        </tbody>
                    </table>
                </div>

            </div>
        )
    }
}

export default ListPhoneNumber

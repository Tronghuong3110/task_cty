import React, { Component } from 'react'
import './sass/simlist.scss';
import '..//../node_modules/bootstrap/dist/css/bootstrap.min.css'
import Subcontainer from './Subcontainer';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import {
    faList, faSimCard, faPlus,
    faMobileScreenButton, faFileExcel, faPenToSquare, faTrashCan, faCircleXmark, faNoteSticky, faFloppyDisk
} from '@fortawesome/free-solid-svg-icons'
import axios from 'axios'
export class Simlist extends Component {
    constructor() {
        super();
        this.state = {
            checkbox : true,
            listSim: [],
            id : null,
            displayWindow: false
        }
        this.removedListSim = []
    }
    hideSubcontainer = () => {
        this.setState({ displayWindow: false });
        this.fetchData()
    }
    // Hiện thị thông tin chi tiết 1 danh sách
    showAddWindow =  (id) => {
        if (sessionStorage.getItem("running") == 1) {
            alert("Tiến trình đang chạy không được phép sửa")
        }
        else{
            this.setState({
                id:id,
                displayWindow : true,}
            )
        } 
    }
    //Tích chọn toàn bộ danh sách
    tickAllCheckbox = () => {
        const tmp = { ...this.state }
        document.querySelectorAll("input[type='checkbox']").forEach(function (checkbox) {
            checkbox.checked = tmp.checkbox;
        })
        this.setState(prevState => ({
            variables: {
                checkbox: prevState.checkbox === true ? false : true
            }
        }))
    }
    // Xóa 1 danh sach sim
    removeListSim = async (id) => {
        if (sessionStorage.getItem("running") == 1) {
            alert("Tiến trình đang chạy không được phép xóa")
        }
        else {
            let removeList = []
            if (typeof id === "string") {
                removeList.push(id)
            }
            else removeList = [...id]
            let config = {
                headers: {
                    "ids": removeList
                }
            }
            console.log(removeList)
            await axios.delete("http://localhost:8081/api/listsim", config)
            alert("Xóa danh sách sim thành công !")
            this.componentDidMount()
        }
    }

    // Thêm 1 danh sách sim 
    addSimList = (name, file) => {
        var formData = new FormData()
        formData.append('file', file)
        formData.append('name', name)
        const config = {
            headers: { 'content-type': 'multipart/form-data' }
        }
        console.log(formData.get('file'))
        axios.post('http://localhost:8081/api/file', formData, {
            headers: { 'content-type': 'multipart/form-data' }
        })
            .then((response) => {
                alert('File uploaded successfully.');
                this.componentDidMount()
            })
            .catch((error) => {
                alert('Error uploading file.');
            });
    }
    // Hiển thị danh sách các danh sách sim
    removeMultiSimList = () => {
        if(sessionStorage.getItem("running") == 1) {
            alert("Tiến trình đang chạy không được phép xóa")
        }
        else {
            let lists = document.querySelectorAll("input[type='checkbox']");
            lists.forEach(list => {
                if (list.checked === true) {
                    this.removedListSim.push(list.getAttribute("list_id"))
                }
            })
            this.removeListSim(this.removedListSim)
            // console.log(this.removedListSim)
            this.removedListSim = []

        }
    }
    fetchData = async () => {
        let res = await axios.get("http://localhost:8081/api/listsim")
        // 'http://localhost:8081/api/listsim'
        this.setState({
            listSim: res && res.data ? res.data : []
        })
    }
    componentDidMount() {
        this.fetchData()
    }
    render() {
        const listSim = { ...this.state.listSim }
        const display  = this.state.displayWindow
        const id = this.state.id
        console.log(display)
        return (
            <div className="simlist-container">
                <div className="header">
                    <div className="header-symbol">
                        <FontAwesomeIcon icon={faList} style={{ color: "#ffffff", fontSize: "2rem", padding: "10px" }} />
                    </div>
                    <div className="header-text">Danh sách sim</div>
                </div>
                <main>
                    <div className="searcher d-flex">
                        <form
                            action=""
                            className="d-flex justify-content-between"
                        >
                            <div className="searcher-inputTel d-flex align-items-center">
                                <FontAwesomeIcon icon={faMobileScreenButton} style={{
                                    position: "absolute",
                                    left: "18px",
                                    color: "#24c79f"

                                }} />
                                <input
                                    type="text"
                                    name="listName"
                                    id="newListName"
                                    placeholder="Nhập tên danh sách...."
                                />
                            </div>
                            <div className="uploadFile">
                                <label htmlFor="file-upload" className="custom-file-upload">
                                    <FontAwesomeIcon icon={faFileExcel} style={{ color: "#24c79f" }} />
                                    {" "}
                                    Tải file excel tại đây
                                </label>
                                <input id="file-upload" type="file" />
                            </div>
                            <button
                                onClick={(e) => {
                                    let name = document.querySelector("#newListName").value
                                    let file = document.querySelector("#file-upload").files[0]
                                    e.preventDefault();
                                    this.addSimList(name, file)
                                }}
                            >Thêm mới</button>
                        </form>
                    </div>
                    <table className="mt-4 table">
                        <thead className="text-center">
                            <tr>
                                <th>
                                    <button onClick={this.tickAllCheckbox}>
                                        Tất cả
                                    </button>
                                </th>
                                <th>STT</th>
                                <th>Tên danh sách</th>
                                <th>Số lượng sim</th>
                                <th>Ghi chú</th>
                                <th>Tác vụ</th>
                            </tr>
                        </thead>
                        <tbody className="text-center">
                            {

                                Object.values(listSim).map((list, index) => {
                                    return (
                                        <tr key={list.id}>
                                            <td>
                                                <input type="checkbox" name="" list_id={list.id}
                                                />
                                            </td>
                                            <td>{index + 1}</td>
                                            <td>{list.name}</td>
                                            <td>{list.totalPhoneNumber}</td>
                                            <td>{list.note}</td>
                                            <td>
                                                <button className="action-btn edit-btn" onClick={() => {
                                                    this.showAddWindow(list.id)
                                                }}>
                                                    <FontAwesomeIcon icon={faPenToSquare} style={{ color: "#24c79f" }} />
                                                </button>
                                                <button className="action-btn remove-btn"
                                                    onClick={(e) => {
                                                        const checkbox = e.currentTarget.closest("tr").querySelector("input[type='checkbox']");
                                                        if(window.confirm("Bạn có chắc chắn muốn xóa danh sách này ? ")) this.removeListSim(checkbox.getAttribute("list_id"))
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
                    <div className='mullti_delete d-flex justify-content-end'
                        onClick={this.removeMultiSimList}
                    >
                        <button className='btn btn-outline-danger' id="remove-many">Xóa</button>
                    </div>
                </main>
                {   display&&<Subcontainer hideSubcontainer={this.hideSubcontainer} id={id} />}
                {/* <div className="sub-container" style={{ display: "none" }}>
                    <div className="editCard">
                        <div className="editCard-delete">
                            <button onClick={this.hideAddWindow}>
                                <FontAwesomeIcon icon={faCircleXmark} style={{ color: "#cc3f3f" }} />
                            </button>
                        </div>
                        <div className="editCard-name">
                            <input type="hidden" name="id" defaultValue={listSimCunrrentOpen.id} />
                            <label htmlFor="listName">
                                <FontAwesomeIcon icon={faPenToSquare} style={{ color: "#ffffff" }} />{" "}
                                Tên danh sách
                            </label>
                            <br />
                            <input
                                type="text"
                                name="listName"
                                defaultValue={listSimCunrrentOpen.name}
                                placeholder="Điền tên danh sách...."
                            />
                        </div>
                        <div className="editCard-list">
                            <label htmlFor="listNumber">
                                <FontAwesomeIcon icon={faSimCard} style={{ color: "#ffffff" }} />{" "}
                                Danh sách sim
                            </label>
                            <div className="editCard-list-searchOrAdd">
                                <input
                                    type="text"
                                    name="listNumber"
                                    defaultValue=""
                                    placeholder="Thêm mới sim tại đây...."
                                />
                                <button
                                    onClick={() => {
                                        this.addNewPhoneToList(document.querySelector("input[name='listNumber']").value)
                                    }}
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
                                            Object.values(listSimCunrrentOpen.listPhoneNumber).map((phone, index) => {
                                                var sdt = phone.phoneNumber
                                                return (
                                                    <tr key={index}>
                                                        <td>{index + 1}</td>
                                                        <td>
                                                            <input type="tel" name="" id="" defaultValue={sdt}
                                                                onBlur={(e) => {
                                                                    this.addToUpdatedPhoneList(phone.id, e.target.value)
                                                                }}
                                                            />
                                                        </td>
                                                        <td>
                                                            <button className="action-btn remove-btn"
                                                                onClick={() => {
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
                        <div className="editCard-note">
                            <label htmlFor="note">
                                <i
                                    className="fa-regular fa-note-sticky"
                                    style={{ color: "#ffffff" }}
                                />
                                <FontAwesomeIcon icon={faNoteSticky} style={{ color: "#ffffff" }} /> Thêm
                                Ghi chú
                            </label>
                            <br />
                            <textarea name="note" id="note" rows={10} defaultValue={listSimCunrrentOpen.note} />
                        </div>
                        <div className="editCard-save">
                            <button onClick={this.updateSimList}>
                                <FontAwesomeIcon icon={faFloppyDisk} style={{ color: "#ffffff" }} />{" "}
                                Lưu
                            </button>
                        </div>
                    </div>
                </div> */}
            </div>
        )
    }
}

export default Simlist
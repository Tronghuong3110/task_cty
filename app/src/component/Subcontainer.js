import React, { Component } from 'react'
import './sass/subcontainer.scss'
import '..//../node_modules/bootstrap/dist/css/bootstrap.min.css'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import {
    faList, faSimCard, faPlus,
    faMobileScreenButton, faFileExcel, faPenToSquare, faTrashCan, faCircleXmark, faNoteSticky, faFloppyDisk
} from '@fortawesome/free-solid-svg-icons'
import axios from 'axios'
import ListPhoneNumber from './ListPhoneNumber'
export class Subcontainer extends Component {
    constructor(props) {
        super(props)
        this.state = {
            openWindowObject: {
                id: '',
                name: '',
                listPhoneNumber: [],
                note: ''
            },
            form: {
                listName: "",
                note: ""
            }
        }
    }
    hideAddWindow = () => {
        this.props.hideSubcontainer();
    }
    handleInput = (e) => {
        const data = { ...this.state.form }
        data[e.target.name] = e.target.value;
    
        this.setState({
            form: {
                ...this.state.form,
                listName: data.listName,
                note: data.note,
            }
        });
    }
    updateSimList = async (e) => {
        e.preventDefault();
        const id  = this.props.id
        const name = this.state.form.listName
        const note = this.state.form.note

        console.log(name,note)
        let data ={
            id: id,
            name : name,
            note : note
        }

        await axios.put("http://localhost:8081/api/listsim", data)
            .then((response) => {
                console.log(response)
                this.hideAddWindow()
                alert("Cập nhật danh sách sim thành công!")
            })
    }
    componentDidMount = async () => {
        const  id  = this.props.id;
        document.querySelector('.sub-container').style.display = 'block';
        let res = await axios.get("http://localhost:8081/api/phoneNumber/list?id=" + id);
        let data = res.data
        this.setState({
            openWindowObject: {
                id: data.id,
                name: data.name,
                listPhoneNumber: data.listPhoneNumber,
                note: data.note
            },
            form:{
                inputName : data.name,
                note : data.note
            }
        })

    }
    render() {
        const listSimCunrrentOpen = this.state.openWindowObject
        // const inputValue = this.state.form.listNumber
        const id = this.props.id
        return (
            <div>
                <div className="sub-container" style={{ display: 'none' }}>
                    <div className="editCard">
                        <div className="editCard-delete">
                            <button
                                onClick={this.hideAddWindow}
                            >
                                <FontAwesomeIcon icon={faCircleXmark} style={{ color: "#cc3f3f" }} />
                            </button>
                        </div>
                        <div className="editCard-name">
                            <input type="hidden" name="id"
                                defaultValue={listSimCunrrentOpen.id}
                            />
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
                                onChange={this.handleInput}
                            />
                        </div>
                        <div className="editCard-list">
                            <label htmlFor="listNumber">
                                <FontAwesomeIcon icon={faSimCard} style={{ color: "#ffffff" }} />{" "}
                                Danh sách sim
                            </label>
                            <ListPhoneNumber id={id} />
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
                            <textarea name="note" id="note" rows={10}
                                defaultValue={listSimCunrrentOpen.note}
                                onChange={this.handleInput}
                            />
                        </div>
                        <div className="editCard-save">
                            <button onClick={this.updateSimList}>
                                <FontAwesomeIcon icon={faFloppyDisk} style={{ color: "#ffffff" }} />{" "}
                                Lưu
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}

export default Subcontainer
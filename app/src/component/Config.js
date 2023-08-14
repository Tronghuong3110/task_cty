import React, { Component } from 'react'
import './sass/config.scss';
import '..//../node_modules/bootstrap/dist/css/bootstrap.min.css'
import axios from 'axios'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faToolbox, faUser, faKey, faSimCard,faPlus } from '@fortawesome/free-solid-svg-icons'
export class Config extends Component {
    constructor(){
        super()
        this.apiKey = ""
        this.state ={
            listSim: [],
            listProFile:[],
            profile: ""
        }
    }
    addConfigInfo= async (profilePath,idListSim,newProfilePath)=>{
        await axios.post("http://localhost:8081/api/config?profilePath="+profilePath+"&idListSim="+idListSim+"&newProfilePath="+newProfilePath)
            .then(response=>{
                sessionStorage.setItem("idConfig", response.data)
                console.log(response.data)
                alert("Lưu cấu hình thành công")
                this.componentDidMount()
            })
    }
    fillSimListSelection = async ()=>{
        var listSim;
        await axios.get("http://localhost:8081/api/listsim")
            .then(response=>{
                listSim = response.data && response ? response.data: []    
            })
        this.setState({listSim:listSim})
    }
    fillProFileSelection = async ()=>{
        var listProFile;
        await axios.get("http://localhost:8081/api/profiles")
            .then(response=>{
                listProFile = response.data && response ? response.data: []    
            })
        this.setState({listProFile:listProFile})
    }
    componentDidMount = async () =>{
        document.getElementById("newProfile").value = ""
        await this.fillProFileSelection()
        await this.fillSimListSelection()
        if(sessionStorage.getItem("running")==1){
            document.getElementById("Profile-Path").setAttribute("disabled", "true");
            document.getElementById("select-sim").setAttribute("disabled", "true");
        }
        else {
            document.getElementById("Profile-Path").removeAttribute("disabled")
            document.getElementById("select-sim").removeAttribute("disabled")
        }
    }
    
    render() {
        const {listProFile,listSim } = {...this.state}
        return (
            <div className='config-container'>
                <div className="header">
                    <div className="header-symbol">
                        <FontAwesomeIcon icon={faToolbox} style={{ color: "#ffffff", fontSize: "2rem", padding: "10px" }} />
                    </div>
                    <div className="header-text">Cấu hình</div>
                </div>
                <main>
                    <form className="form-submit">
                        <div className="form-group mb-5 mt-3 d-flex ">
                            <label
                                htmlFor="Profile-Path"
                                className="col-form-label back-ground-label br"
                            >
                                <FontAwesomeIcon icon={faPlus} style={{ color: "#ffffff", fontSize: 22 }} />
                            </label>
                            <input
                                type="text"
                                className="form-control back-ground-input border border-dark"
                                id="newProfile"
                                placeholder="Thêm mới profile tại đây...."
                                onBlur={(e)=>{
                                    if (e.target.value){
                                        document.getElementById("Profile-Path").setAttribute("disabled", "true");
                                        document.getElementById("select-sim").setAttribute("disabled", "true");
                                    }
                                    else{
                                        document.getElementById("Profile-Path").removeAttribute("disabled")
                                        document.getElementById("select-sim").removeAttribute("disabled")
                                    }
                                }}
                            />
                        </div>
                        <div className="form-group mb-5 d-flex ">
                            <label
                                htmlFor="Profile-Path"
                                className="col-form-label back-ground-label br"
                            >
                                <FontAwesomeIcon icon={faUser} style={{ color: "#ffffff", fontSize: 22 }} />
                            </label>
                            <select
                                className="form-control back-ground-input border border-dark"
                                id="Profile-Path"
                                onBlur={(e)=>{
                                    if(e.target.value!="") document.getElementById("newProfile").setAttribute("disabled","true")
                                    else document.getElementById("newProfile").removeAttribute("disabled")
                                }}
                            >
                                <option value="">Chọn profile tại đây...</option>
                                {
                                    listProFile.map((profile,index)=>{
                                        return(
                                            <option key={index} value={profile.id}>{profile.profilePath}</option>
                                        )
                                    })

                                }
                            </select>

                        </div>
                        <div className="form-group mb-5 d-flex ">
                            <label htmlFor="" className="col-form-label back-ground-label br">
                                <FontAwesomeIcon icon={faSimCard} style={{ color: "#ffffff", fontSize: 22 }} />
                            </label>
                            <select
                                className="form-control back-ground-input border border-dark"
                                id="select-sim"
                                onBlur={(e)=>{
                                    console.log(e.target.value)
                                    if(e.target.value!=="") document.getElementById("newProfile").setAttribute("disabled","true")
                                    else document.getElementById("newProfile").removeAttribute("disabled")
                                }}
                            >
                                <option value="">Chọn danh sách sim</option>
                                {
                                    listSim.map((sim,index)=>{
                                        return(
                                            <option key={index} value={sim.id}>{sim.name}</option>
                                        )
                                    })

                                }
                            </select>
                        </div>
                        <div className="btn-container">
                            <button type="submit" className="button-submit br" id="save-config"
                            onClick={(e)=>{
                                e.preventDefault();
                                let profilePath = document.getElementById("Profile-Path").value
                                let idListSim = document.getElementById("select-sim").value
                                let newProfilePath = document.getElementById("newProfile").value
                                if(newProfilePath!='' ){
                                    console.log("newProfilePath " + newProfilePath)
                                    this.addConfigInfo(-1, "", newProfilePath)
                                }
                                else{
                                    console.log("profilePath" + profilePath)
                                    console.log("idListSim" + idListSim)
                                    if(profilePath != '' && idListSim != ''){
                                        this.addConfigInfo(profilePath,idListSim,"")
                                        localStorage.clear()
                                    }
                                    else alert("Vui lòng chọn đủ thông tin")
                                }
                            }}
                            >
                                Lưu
                            </button>
                        </div>
                    </form>
                </main>
            </div>
        )
    }
}

export default Config
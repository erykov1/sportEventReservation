import React, { useRef, useState, useEffect } from "react";
import axios from "axios";
import { useNavigate, useParams } from "react-router-dom";

const Report = () => {
  const [name, setName] = useState("")
  const [surname, setSurname] = useState("")
  const [email, setEmail] = useState("")
  const [reportCreatedId, setReportCreatedId] = useState("")
  const [hasReachMaxParticipants, setHasReachMaxParticipants] = useState(false)
  const [paymentId, setPaymentId] = useState(null)
  const [tasks, setTasks] = useState([])
  const [task, setTask] = useState(null)
  const {sportEventId} = useParams()
  const navigate = useNavigate()
  const userPaymentIdRef = useRef();
  const reportIdRef = useRef()

  useEffect(() => {
    userPaymentIdRef.current = paymentId;
    reportIdRef.current = reportCreatedId
  }, [paymentId, reportCreatedId]);

  const startProcessInstance = (event) => {
    event.preventDefault()
    axios({
      method: "post",
      url: 'http://localhost:8080/api/report/start',
      headers: {
        'Content-Type': 'application/json',
      },
      data: {
        name: name,
        surname: surname,
        email: email,
        sportEventId: sportEventId,
      }
    }).then((response) => {
      setTimeout(() => {startProcess(reportIdRef)}, 15000)
    }).catch((error) =>{
      console.log(error);
    });
  };

  const startProcess = () => {
    axios({
      method: "get",
      url: 'http://localhost:8080/api/payment/tasks',
      headers: {
        'Content-Type': 'application/json',
      },
    }).then((response) => {
      const task = response.data
      setTask(task)
      navigate(`/payment`, {
        state: {
          email: task.variables.find((value) => value.name === "email").value,
          reportId: task.variables.find((value) => value.name === "reportId").value,
          paymentId: task.variables.find((value) => value.name === "paymentId").value,
          taskId: task.id
        }
      })
    }).catch((error) =>{
      console.log(error);
    });
  } 

  const sendDeclinedEmail = () => {
    axios({
      method: "post",
      url: "http://localhost:8080/api/email/send",
      data: {
        type: "MaxParticipants",
        to: email
      }
    }).then((response) => {
      declineReport(reportCreatedId)
    }).catch((error) =>{
      console.log(error)
    })
  }

  const declineReport = (reportId) => {
    axios({
      method: "patch",
      url: `http://localhost:8080/api/report/decline/${reportId}`,
    }).then((response) => {
      navigate("/")
    }).catch((error) =>{
      console.log(error)
    })
  }

  return (
    <div className="report-container">
      <form>
        <div className="mb-3">
          <label htmlFor="nameInput" className="form-label">
            Imię
          </label>
          <input type="text" className="form-control" id="nameInput" onChange={(e) => setName(e.target.value)}/>
        </div>
        <div className="mb-3">
          <label htmlFor="surnameInput" className="form-label">
            Nazwisko
          </label>
          <input type="text" className="form-control" id="surnameInput" onChange={(e) => setSurname(e.target.value)}/>
        </div>
        <div className="mb-3">
          <label htmlFor="emailInput" className="form-label">
            Email
          </label>
          <input type="text" className="form-control" id="emailInput" onChange={(e) => setEmail(e.target.value)}/>
        </div>
        <button type="submit" className="btn btn-primary" onClick={startProcessInstance}>
          Utwórz zgłoszenie i przejdz do płatności
        </button>
      </form>
    </div>
  )
}

export default Report;
import React, { useEffect } from "react";
import axios from "axios";
import { useParams, useLocation, useNavigate } from "react-router-dom";

const Payment = () => {
  const location = useLocation()
  const { state } = location;
  const email = state ? state.email : null;
  const reportId = state ? state.reportId : null;
  const taskId = state ? state.taskId : null;
  const paymentId = state ? state.paymentId : null;
  const navigate = useNavigate()

  useEffect(() => {
    console.log("z location email ", email)
    console.log("z location reportId ", reportId)
    console.log("z location taskId", taskId)
    console.log("z location paymentId ", paymentId)
  }, [])

  const cancelPayment = () => {
    axios({
      method: 'patch',
      url: `http://localhost:8080/api/payment/cancel/${taskId}/${paymentId}`,
      headers: {
        'Content-Type': 'application/json',
      }
    }).then((response) => {
      declineReport()
    }).catch((error) => {
      console.log(error);
    });
  }

  const acceptPayment = () => {
    axios({
      method: 'patch',
      url: `http://localhost:8080/api/payment/accept/${taskId}/${paymentId}`,
      headers: {
        'Content-Type': 'application/json',
      }
    }).then((response) => {
      acceptReport()
    }).catch((error) => {
      console.log(error);
    });
  }

  const sendCanceledPaymentEmail = () => {
    axios({
      method: "post",
      url: "http://localhost:8080/api/email/send",
      data: {
        type: "DeclinedPayment",
        to: email
      }
    }).then((response) => {
    }).catch((error) =>{
      console.log(error)
    })
  }

  const sendAcceptedReportEmail = () => {
    axios({
      method: "post",
      url: "http://localhost:8080/api/email/send",
      data: {
        type: "Accepted",
        to: email
      }
    }).then((response) => {
    }).catch((error) =>{
      console.log(error)
    })
  }

  const declineReport = () => {
    axios({
      method: "patch",
      url: `http://localhost:8080/api/report/decline/${reportId}`,
    }).then((response) => {
      sendCanceledPaymentEmail()
      navigate("/")
    }).catch((error) =>{
      console.log(error)
    })
  }

  const acceptReport = () => {
    axios({
      method: "patch",
      url: `http://localhost:8080/api/report/accept/${reportId}`,
    }).then((response) => {
      sendAcceptedReportEmail()
      navigate("/")
    }).catch((error) =>{
      console.log(error)
    })
  }

  return (
    <div className="payment-container">
      <button type="button" class="btn btn-primary" onClick={acceptPayment}>Potwierdź płatność</button>
      <button type="button" class="btn btn-warning" onClick={cancelPayment}>Odrzuć płatność</button>
    </div>
  )
}

export default Payment;
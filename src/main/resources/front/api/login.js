function loginApi(data) {
    return $axios({
      'url': '/user/login',
      'method': 'post',
      data
    })
}

function loginEmailApi(data) {
    return $axios({
        'url': '/user/emailLogin',
        'method': 'post',
        data
    })
}

function sendMsgApi(data) {
    return $axios({
        'url': '/user/sendMsg',
        'method': 'post',
        data
    })
}
function sendEmailApi(data) {
    return $axios({
        'url': '/user/sendEmail',
        'method': 'post',
        data
    })
}

function loginoutApi() {
  return $axios({
    'url': '/user/loginout',
    'method': 'post',
  })
}

  
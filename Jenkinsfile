
pipeline {
    agent {
      docker {
        image 'maven:3-alpine'
        args '-v /root/.m2:/root/.m2'
      }
    }
    stages {
	stage('Look arround') {
	    steps {
		sh 'echo "${BRANCH_NAME}"'
	    }
	}
        stage('Build') {
            steps {
                sh 'mvn -B -DskipTests clean install'
            }
        }
        stage('Test') {
            steps {
                sh 'mvn test'
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }
        stage('Deliver') {
            steps {
                sh './jenkins/deliver.sh'
            }
        }
        stage('Deliver for development') {
          when {
            branch 'develop'
          }
          steps {
            sh 'mvn -B -DskipTests clean package'
            input message: 'Finished using the web site? (Click "Proceed" to continue)'
            sh 'echo yes was clicked'
          }
        }
        stage('Deploy for production') {
          when {
            branch 'master'
          }
          steps {
            sh 'mvn -B -DskipTests clean install'
            input message: 'Finished using the web site? (Click "Proceed" to continue)'
            sh 'echo yes was clicked'
          }
        }
    }
}

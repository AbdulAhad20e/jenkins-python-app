pipeline {
    agent any
    
    tools {
        maven 'Maven-3.9.11'
    }
    
    stages {
        stage('Checkout') {
            steps {
                git branch: 'master', 
                url: 'https://github.com/kriru/firstJava.git'
            }
        }
        
        stage('Build') {
            steps {
                bat 'mvn clean compile'
            }
        }
        
        stage('Static Analysis') {
            steps {
                bat 'mvn findbugs:findbugs pmd:pmd checkstyle:checkstyle'
            }
            
            post {
                always {
                    echo 'Static analysis completed'
                }
            }
        }
        
        stage('Test') {
            steps {
                bat 'mvn test'
            }
        }
    }
    
    post {
        always {
            echo 'Pipeline completed'
        }
        success {
            echo 'Pipeline succeeded!'
        }
        failure {
            echo 'Pipeline failed!'
        }
    }
}

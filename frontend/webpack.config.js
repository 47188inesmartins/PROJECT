function delay(ms) {
    return new Promise((resolve, reject) => {
        setTimeout(resolve, ms)
    })
}

module.exports = {
    mode: "development",
    resolve: {
        extensions: [".js", ".ts", ".tsx"],
        alias: {
            'mdbreact': 'cdbreact/lib' // Adicione esse alias para importar 'cdbreact' como 'mdbreact'
        },
    },
    devServer: {
        port: 8000,
        historyApiFallback: true,
        compress: false, 
        proxy: {
            "/api": {
                target: "http://localhost:8080",
                // introducing an API delay to make testing easier
               // pathRewrite: {'^/api' : ''}
            }
        },
    },
    module: {
        rules: [
            {
                test: /\.tsx?$/,
                use: 'ts-loader',
                exclude: /node_modules/
            },
            {
                test: /\.css$/, // O padrão dos arquivos CSS
                use: ['style-loader', 'css-loader'], // Usar os loaders style-loader e css-loader para lidar com os arquivos CSS
            },
        ]
    }
}

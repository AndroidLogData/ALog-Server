const packageJSON = require('./package.json');
const path = require('path');
const webpack = require('webpack');
// const CleanWebpackPlugin = require('clean-webpack-plugin');

const PATHS = {
    build: path.resolve(__dirname, 'target', 'classes', 'META-INF', 'resources', 'webjars', packageJSON.name, packageJSON.version)
};

module.exports = {
    entry: {
        app: [
            './src/main/resources/static/react/index'
        ]
    },
    output: {
        path: PATHS.build,
        filename: 'bundle.js',
        publicPath: "/assets/"
    },
    devServer: {
        hot: true,
        historyApiFallback: true,
        inline: true,
        port: 3000,
        publicPath: "/assets/",
        contentBase: './src/main/resources/templates',
        proxy: {
            "**": "http://localhost:8080"
        }
    },
    module: {
        rules: [{
            test: /\.js$/,
            exclude: /node_modules/,
            use: {
                loader: 'babel-loader',
                options: {
                    cacheDirectory: true,
                    presets: ['react'],
                }
            }
        },
            {
                test: /\.css$/,
                use: [
                    'style-loader',
                    'css-loader'
                ]
            },
            {
                test: /\.(png|svg|jpg|gif)$/,
                use: [
                    'file-loader'
                ]
            },
            {
                test: /\.(woff|woff2|eot|ttf|otf)$/,
                use: [
                    'file-loader'
                ]
            }
        ]
    },
    resolve: {
        modules: ['node_modules'],
        extensions: ['.js', '.json', '.jsx', '.css'],
    },
    devtool: 'inline-source-map',
    plugins: [
        // new CleanWebpackPlugin([PATHS.build])
        new webpack.HotModuleReplacementPlugin()
    ]
};
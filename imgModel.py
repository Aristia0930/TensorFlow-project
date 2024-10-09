import tensorflow as tf
from tensorflow.keras import layers, models
from tensorflow.keras.preprocessing.image import ImageDataGenerator

# 데이터셋 경로 설정
train_dir = 'pyModel/train'
validation_dir = 'pyModel/validation'

# 이미지 데이터 전처리
train_datagen = ImageDataGenerator(rescale=1./255)
validation_datagen = ImageDataGenerator(rescale=1./255)

train_generator = train_datagen.flow_from_directory(
    train_dir,
    target_size=(150, 150),
    batch_size=32,
    class_mode='categorical')  # 다중 분류를 위한 'categorical'

validation_generator = validation_datagen.flow_from_directory(
    validation_dir,
    target_size=(150, 150),
    batch_size=32,
    class_mode='categorical')

# CNN 모델 정의
model = models.Sequential([
    layers.Conv2D(32, (3, 3), activation='relu', input_shape=(150, 150, 3)),
    layers.MaxPooling2D(2, 2),
    layers.Conv2D(64, (3, 3), activation='relu'),
    layers.MaxPooling2D(2, 2),
    layers.Conv2D(128, (3, 3), activation='relu'),
    layers.MaxPooling2D(2, 2),
    layers.Flatten(),
    layers.Dense(512, activation='relu'),
    layers.Dense(2, activation='softmax')  # 클래스 수 수정
])

model.compile(optimizer='adam',
              loss='categorical_crossentropy',  # 다중 분류를 위한 손실 함수
              metrics=['accuracy'])



# 모델 학습
history = model.fit(
    train_generator,
    steps_per_epoch=100,
    epochs=10,
    validation_data=validation_generator,
    validation_steps=50)


model.save("img_model", save_format='tf')

# model = tf.keras.models.load_model('C:/Users/kjk98/OneDrive/바탕 화면/TensorFlowproject/img_model')

# # 그래프에서 각 노드 이름 출력
# print(model.signatures)# print("입력")
# for layer in model.layers:
#     print(layer.name)
# input_layer_name = model.layers[0].name  # 첫 번째 레이어가 입력 레이어입니다.
# print("Input layer name:", input_layer_name)

# # 출력 레이어 이름 가져오기
# output_layer_name = model.layers[-1].name  # 마지막 레이어가 출력 레이어입니다.
# print("Output layer name:", output_layer_name)

# model.summary()